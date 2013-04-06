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
    private Vector2f velocity;
    private Image bullet;
    
    public Bullet(EntityManager manager, Vector2f position, int dmg, Entity.Alliance alnc)
    {
        super(manager);
        pos.set(position);
        size = new Vector2f(SIZE_WIDTH, SIZE_HEIGHT);
        damage = dmg;
        velocity = new Vector2f(0, 0);
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
        pos.x += velocity.x * delta / 1000;
        pos.y += velocity.y * delta / 1000;
        
        RectShape rect = (RectShape)shape;
        rect.pos.set(this.pos);
        
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
        
        //Rotate the bullet image according to the angle of the vector
        //relative to (1, 0), or zero degrees
        
        Vector2f direction = velocity.copy().normalise();
        Vector2f zeroDegrees = new Vector2f(1, 0);
        
        float dotProduct = direction.x * zeroDegrees.x + direction.y * zeroDegrees.y;
        float cosTheta = dotProduct / (direction.length() * zeroDegrees.length());
        float angle = (float)Math.acos(cosTheta);
        angle *= (180 / Math.PI);
        
        if (direction.y < 0)
            angle *= -1;
        
        bullet.rotate(angle);
    }
    
    public void Collide(Entity other)
    {
        if(other instanceof Enemy)
        {
            if(alliance != ((Enemy)other).getAlliance())
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


    public Entity.Alliance getAlliance() {
        return alliance;
    }
}