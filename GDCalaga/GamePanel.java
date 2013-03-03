import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.*;
import javax.imageio.ImageIO;

public class GamePanel extends JPanel
{
    public int fps = 60;
    public int frameCount = 0;
    float interpolation;
    Image img;//=Toolkit.getDefaultToolkit().getImage("Pics/BackGroundTest2.png");
    boolean paused;
    Player player;
    ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    
    EntityManager Entities = new EntityManager();
    InputHandler Input = new InputHandler();
    
    public GamePanel(int WIDTH, int HEIGHT)
    {
        
        
        addKeyListener(Input);
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
            
            if(Input.IsKeyDown(KeyEvent.VK_W)){
                player.moveUp();
            }
            if(Input.IsKeyDown(KeyEvent.VK_S)){
                player.moveDown();
            }
            if(Input.IsKeyDown(KeyEvent.VK_A)){
                player.moveLeft();
            }
            if(Input.IsKeyDown(KeyEvent.VK_D)){
                player.moveRight();
            }
            
            
            if(Input.WasKeyPressed(KeyEvent.VK_SPACE))
            {
                player.fire();
            }
            
        }
        
        else
        {
            
        }
        
        
        if(Input.WasKeyPressed(KeyEvent.VK_H))
        {
            screenShot();
        }
        
        
        if(Input.WasKeyPressed(KeyEvent.VK_ESCAPE))
        {
            paused=!paused;
        }
        
        Input.update();
    }
    
    public void paintComponent(Graphics g)
    {
        
        //BS way of clearing out the old rectangle to save CPU.
        g.setColor( new Color(0,0,0));//R,G,B
        g.fillRect(0, 0,getWidth(),getHeight());
        //g.drawImage(img,0,0,null); //Deleting this segment will stop the image buffering, cool effects
        
        float interp = paused ? 0 : interpolation;
        Entities.draw(g, interp);
        
        
        g.setColor(Color.WHITE);
        g.drawString("Player HP: " + player.getHealth(), 5, 10);
        
        
        g.dispose();
        
        frameCount++;
    }
    
    public void resetFPS(){
        fps = frameCount;
        frameCount=0;
    }
    
    public void screenShot()
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
    
    
    public void resetFocus()
    {
        setFocusable(true);
    }
    
    
}