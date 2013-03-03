import java.awt.*;


public class Bullet
{
    private float x, y, damage, lastX, lastY, xVel, yVel;
    private int  width, height;
    private Image bullet;
    
    public Bullet(int xpos, int ypos, int dmg)
    {
        x=xpos;lastX=x;
        y=ypos;lastY=y;
        damage=dmg;
        yVel=0;
        bullet=Toolkit.getDefaultToolkit().getImage("Pics/BlueSquaretrans.png");
    }
    
    
    public void update(float delta)
    {
        
        lastX = x;
        lastY = y;
        
        x+= xVel * delta;
        y+= yVel * delta;
        
    }
    
    
    
    public void draw(Graphics g, float interp)
    {
        
        
        Graphics2D g2d=(Graphics2D)g; // Create a Java2D version of g.
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        
        int drawX = (int) ((x - lastX) * interp + lastX - width/2);
        int drawY = (int) ((y - lastY) * interp + lastY - height/2);
        
        
        g2d.drawImage(bullet,drawX,drawY,null);
        g2d.setColor(Color.WHITE);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
        
    }
    
    public void setSpeed(float a, float b)
    {
        xVel=a;
        yVel=b;
    }
    
}