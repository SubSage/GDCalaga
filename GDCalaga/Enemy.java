import org.newdawn.slick.*;


public class Enemy extends Entity
{
    private float x, y, lastX, lastY, xVel, yVel, health;
    private int height, width, alliance;
    Image ship;
    
    public Enemy(EntityManager manager, int xpos, int ypos)
    {
        super(manager);
        x=xpos;lastX=x;
        y=ypos;lastY=y;
        width=50;
        height=50;
        xVel=0;
        yVel=100;
        health=10;
        alliance=0;
        shape = new RectShape(x, y, width, height);
        

        try {
			ship = new Image("Pics/BlueSquaretrans.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
        
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
            
            x+= xVel * delta / 1000;
            y+= yVel * delta / 1000;
            
            
            if(y<0){
                y=0;
                yVel*=-1;
            }
            
            
            if(y>720){
                y=720;
                yVel*=-1;
            }
            
            if(Math.random()*100<5){
                fire();
            }
            
            
        }
        
        
        RectShape rect = (RectShape)shape;
        rect.xpos = x;
        rect.ypos = y;
    }
    
    
    
    public void draw(Graphics g)
    {
        /*
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
        */

        int drawX = (int) (x - width/2);
        int drawY = (int) (y - height/2);
        float scale = width / ship.getWidth();
        ship.draw(drawX, drawY, scale);
    	
    }

    public void Collide(Entity other)
    {
        
    }
    
    public void fire()
    {
        Bullet newBullet = new Bullet(entities, (int)x, (int)y ,1 , alliance);
        newBullet.setSpeed(-250, 0);
    }
    
    
    public void Hurt(float dmg)
    {
        health -= dmg;
        if(health<=0) Destroy();
    }
    
    public int getAlliance(){
        return alliance;
    }

}