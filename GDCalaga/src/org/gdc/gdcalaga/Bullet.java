package org.gdc.gdcalaga;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class Bullet extends Entity
{
	private static final int SIZE_WIDTH = 9;
	private static final int SIZE_HEIGHT = 5;
	
    private float damage;
    private Vector2f velocity, size;
    private int alliance;
    private Image bullet;
    
    public Bullet(EntityManager manager, Vector2f position, int dmg, int alnc)
    {
        super(manager);
        pos = position;
        size.set(SIZE_WIDTH, SIZE_HEIGHT);
        damage = dmg;
        velocity.set(0, 0);
        alliance = alnc;
        
        shape = new RectShape(pos, size);
        
        try {
			bullet = new Image("Pics/Bullet.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
    }
    
    
    public void update(float delta)
    {
        Vector2f adjustedVelocity = velocity.copy().scale(delta / 1000);
        pos.add(adjustedVelocity);
        
        RectShape rect = (RectShape)shape;
        rect.pos = this.pos;
        
        if (pos.x < 0    && velocity.x <= 0 ||
        	pos.x > 1280 && velocity.x >= 0 ||
        	pos.y < 0    && velocity.y <= 0 ||
        	pos.y > 780  && velocity.y >= 0)
        {
        	Destroy();
        }
    }
    
    
    public void draw(Graphics g)
    {
        int drawX = (int)(pos.x - size.x / 2);
        int drawY = (int)(pos.y - size.y / 2);
        float bw = bullet.getWidth();
        float scale = size.x / bw;
        bullet.draw(drawX, drawY, scale, Color.white);
    }
    
    public void setSpeed(float xSpeed, float ySpeed)
    {
        velocity.set(xSpeed, ySpeed);
    }
    
    public void setSpeed(Vector2f speed)
    {
    	velocity = speed;
    }
    
    public void Collide(Entity other)
    {
        if(other instanceof Enemy)
        {
            if(alliance!=((Enemy)other).getAlliance())
            {
                Destroy();
            }
            
        }
        
        else if(other instanceof Player)
        {
            if(alliance!=((Player)other).getAlliance())
            {
                Destroy();
            }
            
        }
    }
    
    public float getDamage(){
    	return damage;
    }


	public int getAlliance() {
		return alliance;
	}
}