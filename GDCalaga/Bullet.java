import java.awt.*;


public class Bullet extends Entity
{
    private float damage, lastX, lastY, xVel, yVel;
    private int  width, height;
    private Image bullet;
    
    public Bullet(EntityManager manager, int xpos, int ypos, int dmg)
    {
        super(manager);
        tag = "bullet";
        x=xpos;lastX=x;
        y=ypos;lastY=y;
        width=10;
        height=10;
        damage=dmg;
        yVel=0;
        bullet=Toolkit.getDefaultToolkit().getImage("Pics/BlueSquaretrans.png");
        
        shape = new RectShape(x, y, width, height);
    }
    
    
    public void update(float delta)
    {
        
        lastX = x;
        lastY = y;
        
        x+= xVel * delta;
        y+= yVel * delta;
        
        
        RectShape rect = (RectShape)shape;
        rect.xpos = x;
        rect.ypos = y;
    }
    
    
    public void draw(Graphics g, float interp)
    {
        
        
        Graphics2D g2d=(Graphics2D)g; // Create a Java2D version of g.
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        
        int drawX = (int) ((x - lastX) * interp + lastX - width/2);
        int drawY = (int) ((y - lastY) * interp + lastY - height/2);
        
        
        g2d.drawImage(bullet,drawX,drawY,width,height,null);
        g2d.setColor(Color.WHITE);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
        
    }
    
    public void setSpeed(float a, float b)
    {
        xVel=a;
        yVel=b;
    }
    
    public void Collide(Entity other)
    {
        if(other.tag == "enemy")
        {
            Enemy enemy = (Enemy)other;
            enemy.Hurt(damage);
            Destroy();
        }
    }
}