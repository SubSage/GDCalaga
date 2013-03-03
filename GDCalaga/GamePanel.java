import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.*;
import javax.imageio.ImageIO;

public class GamePanel extends JPanel implements KeyListener
{
    public int fps = 60;
    public int frameCount = 0;
    float interpolation;
    Image img=Toolkit.getDefaultToolkit().getImage("Pics/BackGroundTest2.png");
    boolean w,s,a,d,paused;
    boolean playerCanFire=true;
    Player player;
    ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    
    EntityManager Entities = new EntityManager();
    
    public GamePanel(int WIDTH, int HEIGHT)
    {
        img=Toolkit.getDefaultToolkit().getImage("Pics/Blankblack.png");
        w=s=false;
        addKeyListener(this);
        player= new Player(Entities,50,300);
        
        for(int a=0;a<1;a++){
            new Enemy(Entities,1200,250);
        }
    }
    
    public void setInterpolation(float interp)
    {
        interpolation = interp;
    }
    
    public void update()
    {
        if(!paused)
        {
        	Collision.CheckCollisions(Entities.GetEntities());
        	
            Entities.update(interpolation);
            
            if(w){
                player.moveUp();
            }
            if(s){
                player.moveDown();
            }
            if(a){
                player.moveLeft();
            }
            if(d){
                player.moveRight();
            }

            
            
        }
        
        else
        {
            
        }
    }
    
    public void paintComponent(Graphics g)
    {
        
        //BS way of clearing out the old rectangle to save CPU.
        g.setColor( new Color(0,0,0));//R,G,B
        g.fillRect(0, 0,getWidth(),getHeight());
        g.drawImage(img,0,0,null); //Deleting this will stop the image buffering, cool effects
        
        float interp = paused ? 0 : interpolation;
    	Entities.draw(g, interp);
        
        
        g.dispose();
        
        frameCount++;
    }
    
    public void resetFPS(){
        fps =frameCount;
        frameCount=0;
    }
    
    public void keyTyped(KeyEvent e){
    }
    
    public void keyPressed(KeyEvent e){
        
        
        if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
        {
            paused=!paused;
        }
        
        if(e.getKeyCode()==KeyEvent.VK_SPACE)
        {
            if(playerCanFire)
            {
                player.fire();
                playerCanFire=false;
            }
            
            
        }
        
        if(e.getKeyCode()==KeyEvent.VK_H)
        {
            
            try
            {
                Scanner reader= new Scanner(new File("screencaptures\\data.txt"));
                int serial=reader.nextInt()+1;
                reader.close();
                Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
                BufferedImage capture;
                capture = new Robot().createScreenCapture(screenRect);
                File outputfile = new File("screencaptures\\"+serial+".png");
                FileWriter fstream = new FileWriter("screencaptures\\data.txt");
                BufferedWriter out = new BufferedWriter(fstream);
                out.write(""+serial);
                out.close();
                ImageIO.write(capture, "png", outputfile);
                System.out.println("screencaptures\\"+serial+".png");
            }
            
            catch (Exception ee)
            {
                System.out.println("Something happened with saving image. Likely didn't save.");
                System.out.println("It's probably that you don't have the \"screencaptures\"");
                System.out.println("folder...or the data file number is wrong...make the data");
                System.out.println("file (txt) and enter the number of the img count");
            }
        }
        
        if(e.getKeyCode()==KeyEvent.VK_W)
        {
            w=true;
        }
        
        if(e.getKeyCode()==KeyEvent.VK_S)
        {
            s=true;
        }
        
        if(e.getKeyCode()==KeyEvent.VK_A)
        {
            a=true;
        }
        
        if(e.getKeyCode()==KeyEvent.VK_D)
        {
            d=true;
        }
        
    }
    
    public void keyReleased(KeyEvent e)
    {
        
        if(e.getKeyCode()==KeyEvent.VK_W)
        {
            w=false;
        }
        
        if(e.getKeyCode()==KeyEvent.VK_S)
        {
            s=false;
        }
        
        if(e.getKeyCode()==KeyEvent.VK_A)
        {
            a=false;
        }
        
        if(e.getKeyCode()==KeyEvent.VK_D)
        {
            d=false;
        }
        
        if(e.getKeyCode()==KeyEvent.VK_SPACE)
        {
            playerCanFire=true;
            
        }
        
    }
    
    public void resetFocus()
    {
        setFocusable(true);
    }
    
    
}