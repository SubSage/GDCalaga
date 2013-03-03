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
    
    public Player(EntityManager manager, int xpos, int ypos)
    {
        super(manager);
        tag = "player";
        x=xpos;lastX=x;
        y=ypos;lastY=y;
        ship=Toolkit.getDefaultToolkit().getImage("Pics/BlueSquare.png");
        width=25;
        height=25;
        xVel=20;
        yVel=20;
        health=10;
        alliance=1;
        bullets = new ArrayList<Bullet>();
        
        shape = new RectShape(x, y, width, height);
        
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
        

        RectShape rect = (RectShape)shape;
        rect.xpos = x;
        rect.ypos = y;
    }
    
    
    
    public void draw(Graphics g, float interp)
    {
        
        if(health>0)
        {
            Graphics2D g2d=(Graphics2D)g; // Create a Java2D version of g.
            
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            
            int drawX = (int) ((x - lastX) * interp + lastX - width/2);
            int drawY = (int) ((y - lastY) * interp + lastY - height/2);
            
            g2d.drawImage(ship,drawX,drawY, width, height,null);
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
    	Bullet newBullet = new Bullet(entities, (int)x,(int)y,1);
    	newBullet.setSpeed(40, 0);
    }

    public void Collide(Entity other){
    	
    }
}