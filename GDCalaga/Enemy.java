import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Enemy extends Entity
{
    private float x, y, lastX, lastY, xVel, yVel, health;
    private int height, width, alliance;
    Image ship;
    
    public Enemy(int xpos, int ypos){
        super();
        x=xpos;lastX=x;
        y=ypos;lastY=y;
        ship=Toolkit.getDefaultToolkit().getImage("Pics/BlueSquaretrans.png");
        width=50;
        height=50;
        xVel=0;
        yVel=10;
        health=10;
        alliance=0;
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
            
            x+= xVel * delta;
            y+= yVel * delta;
            
            
            if(y<10){
                y=10;
                yVel*=-1;
            }
            
            
            if(y>900){
                y=900;
                yVel*=-1;
            }
            
            
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
    
}