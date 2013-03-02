import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Manifest extends JFrame //implements ActionListener
{
    int Width=1920;
    int Height=1280;
    private GamePanel gamePanel = new GamePanel(Width,Height);
    private boolean running = true;
    private boolean paused = false;
    
    
    public Manifest()
    {
        super("Fixed Timestep Game Loop Test");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(1,2));
        cp.add(gamePanel);
        setSize(Width,Height);  //1280--720
        setResizable(true);
        this.setUndecorated(true);
    }
    
    public static void main(String[] args)
    {
        Manifest glt = new Manifest();
        glt.setVisible(true);glt.gameLoop();
    }
    
    
    
    //Starts a new thread and runs the game loop in it.
    public void runGameLoop()
    {
        Thread loop = new Thread()
        {
            public void run()
            {
                gameLoop();
            }
        };
        loop.start();
    }
    
    //Only run this in another Thread!
    private void gameLoop()
    {
        gamePanel.requestFocus();
        
        final double GAME_HERTZ = 30.0;
        final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
        //At the very most we will update the game this many times before a new render.
        //If you're worried about visual hitches more than perfect timing, set this to 1.
        final int MAX_UPDATES_BEFORE_RENDER =50;
        double lastUpdateTime = System.nanoTime();
        double lastRenderTime = System.nanoTime();
        
        //If we are able to get as high as this FPS, don't render again.
        final double TARGET_FPS = 6000;
        final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;
        
        //Simple way of finding FPS.
        int lastSecondTime = (int) (lastUpdateTime / 1000000000);
        
        while(true)
        {
            if(!gamePanel.paused)
            {
                
                double now = System.nanoTime();
                int updateCount = 0;
                
                //Do as many game updates as we need to, potentially playing catchup.
                while( now - lastUpdateTime > TIME_BETWEEN_UPDATES )
                {
                    updateGame();
                    lastUpdateTime += TIME_BETWEEN_UPDATES;
                    updateCount++;
                }
                
                //If for some reason an update takes forever, we don't want to do an insane number of catchups.
                //If you were doing some sort of game that needed to keep EXACT time, you would get rid of this.
                if ( now - lastUpdateTime > TIME_BETWEEN_UPDATES)
                {
                    lastUpdateTime = now - TIME_BETWEEN_UPDATES;
                }
                
                //Render. To do so, we need to calculate interpolation for a smooth render.
                float interpolation = Math.min(1.0f, (float) ((now - lastUpdateTime) / TIME_BETWEEN_UPDATES) );
                drawGame(interpolation);
                lastRenderTime = now;
                
                //Update the frames we got.
                int thisSecond = (int) (lastUpdateTime / 1000000000);
                if (thisSecond > lastSecondTime)
                {
                    gamePanel.resetFPS();
                    lastSecondTime = thisSecond;
                }
                
                //Yield until it has been at least the target time between renders. This saves the CPU from hogging.
                while ( now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES)
                {
                    Thread.yield();
                    
                    //This stops the app from consuming all your CPU. It makes this slightly less accurate, but is worth it.
                    //You can remove this line and it will still work (better), your CPU just climbs on certain OSes.
                    //FYI on some OS's this can cause pretty bad stuttering. Scroll down and have a look at different peoples' solutions to this.
                    try {Thread.sleep(1);} catch(Exception e) {}
                    
                    now = System.nanoTime();
                }
            }
            
        }
    }
    
    private void updateGame()
    {
        gamePanel.update();
    }
    
    private void drawGame(float interpolation)
    {
        gamePanel.setInterpolation(interpolation);
        gamePanel.repaint();
    }
    
    
}