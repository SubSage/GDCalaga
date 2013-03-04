import org.newdawn.slick.*;


public class Bullet extends Entity
{
    private float damage, lastX, lastY, xVel, yVel;
    private int  width, height, alliance;
    private Image bullet;
    
    public Bullet(EntityManager manager, int xpos, int ypos, int dmg, int alnc)
    {
        super(manager);
        x=xpos;lastX=x;
        y=ypos;lastY=y;
        width=10;
        height=10;
        damage=dmg;
        yVel=0;
        xVel=0;
        alliance=alnc;
        
        shape = new RectShape(x, y, width, height);
        

        try {
			bullet = new Image("Pics/BlueSquaretrans.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
    }
    
    
    public void update(float delta)
    {
        
        lastX = x;
        lastY = y;
        
        x+= xVel * delta / 1000;
        y+= yVel * delta / 1000;
        
        
        RectShape rect = (RectShape)shape;
        rect.xpos = x;
        rect.ypos = y;
        
        if( x<0    && xVel<=0 ||
            x>1280 && xVel>=0 ||
            y<0    && yVel<=0 ||
            y>780  && yVel>=0){
                Destroy();
            }
    }
    
    
    public void draw(Graphics g)
    {
        int drawX = (int) (x - width/2);
        int drawY = (int) (y - height/2);
        float bw = bullet.getWidth();
        float scale = width / bw;
        bullet.draw(drawX, drawY, scale);
    }
    
    public void setSpeed(float a, float b)
    {
        xVel=a;
        yVel=b;
    }
    
    public void Collide(Entity other)
    {
        if(other instanceof Enemy)
        {
            Enemy enemy = (Enemy)other;
            if(alliance!=enemy.getAlliance())
            {
                enemy.Hurt(damage);
                Destroy();
            }
            
        }
        
        else if(other instanceof Player)
        {
            Player player = (Player)other;
            if(alliance!=player.getAlliance())
            {
                player.Hurt(damage);
                Destroy();
            }
            
        }
    }
}