import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class Player extends Entity
{
    private float x, y, lastX, lastY, xVel, yVel, health;
    private int height, width, alliance;
    Image ship;
    
    ArrayList<Bullet> bullets;
    
    public Player(int xpos, int ypos)
    {
        super();
        x=xpos;lastX=x;
        y=ypos;lastY=y;
        ship=Toolkit.getDefaultToolkit().getImage("Pics/BlueSquare.png");
        width=50;
        height=50;
        xVel=20;
        yVel=20;
        health=10;
        alliance=1;
        bullets = new ArrayList<Bullet>();
    }
    
    
    public void update(float delta)
    {
        
        if(health<0)
        {
            health=0;
        }
        
        else
        {
            
            
            lastX = x;
            lastY = y;
            
            //x+= xVel * delta;
            //y+= yVel * delta;
            
            
            
        }
        
        
    }
    
    
    
    public void draw(Graphics g, float interp)
    {
        
        if(health>0)
        {
            Graphics2D g2d=(Graphics2D)g; // Create a Java2D version of g.
            
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            
            int drawX = (int) ((x - lastX) * interp + lastX - width/2);
            int drawY = (int) ((y - lastY) * interp + lastY - height/2);
            
            g2d.drawImage(ship,drawX,drawY,null);
            g2d.setColor(Color.WHITE);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
        }
    }
    
    
    public void moveUp()
    {
        y-=yVel;
    }
    
    public void moveDown()
    {
        y+=yVel;
    }
    
    public void moveLeft()
    {
        x-=xVel;
    }
    
    public void moveRight()
    {
        x+=xVel;
    }
    
    public void fire()
    {
        bullets.add(new Bullet((int)x,(int)y,1));
        bullets.get(bullets.size()-1).setSpeed(25,0);
    }
    
}