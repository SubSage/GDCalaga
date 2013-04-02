package org.gdc.gdcalaga;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

/*
 * Upgrades planned for the player:
 * Droppable: Health, shields, rapid fire mode, more bullets
 * Buyable: Speed 
 */

public class Player extends Entity
{
    private static final int SIZE_WIDTH = 49;
    private static final int SIZE_HEIGHT = 29;
    private static final int SPEED = 220;
	
    private float health;
    private Vector2f velocity;
    private Vector2f size;
    private int alliance;
    private static int totalPoints = 0; //the score is static in case we give players multiple lives in the future
    Image ship;
    
    public Player(EntityManager manager, Vector2f position)
    {
        super(manager);

        pos = position;
        size = new Vector2f(SIZE_WIDTH, SIZE_HEIGHT);
        velocity = new Vector2f(SPEED, SPEED);
        
        health = 10;
        alliance = 1;
        
        shape = new RectShape(pos, size);
        
        try {
            ship= new Image("Pics/Player.png");
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
    
    
    public void update(float delta)
    {
    	RectShape rect = (RectShape)shape;
        rect.pos = this.pos;
    }
    
    public void draw(Graphics g)
    {
        int drawX = (int)(pos.x - size.x / 2);
        int drawY = (int)(pos.y - size.y / 2);
        float scale = size.x / ship.getWidth();
        ship.draw(drawX, drawY, scale, Color.white);
    }
    
    
    public void moveUp(float delta)
    {
        pos.y -= velocity.y * delta / 1000;
        pos.y = Math.max(size.y / 2, pos.y);
    }
    
    public void moveDown(float delta)
    {    
        pos.y += velocity.y * delta / 1000;
        pos.y = Math.min(720 - size.y / 2, pos.y);
    }
    
    public void moveLeft(float delta)
    {
        pos.x -= velocity.x * delta / 1000;
        pos.x = Math.max(0 + size.x / 2, pos.x);
    }
    
    public void moveRight(float delta)
    {
        pos.x += velocity.x * delta / 1000;
        pos.x = Math.min(1280 - size.x / 2, pos.x);
    }
    
    public void fire()
    {
    	Vector2f position = new Vector2f(pos.x + size.x / 2, pos.y);
        Bullet newBullet = new Bullet(entities, position, 1, alliance);
        newBullet.setSpeed(500, 0);
    }
    
    public void Collide(Entity other)
    {
    	if(other instanceof Bullet && ((Bullet)other).getAlliance()!=alliance)
    	{
    	    Hurt(((Bullet)other).getDamage());
    	}
    }
    
    public void Hurt(float dmg){
        health-=dmg;
    }
    
    public float getHealth()
    {
        return health;
    }
    
    public int getAlliance()
    {
        return alliance;
    }
    
    public static int getTotalPoints()
    {
    	return totalPoints;
    }
    
    public static void increaseTotalPoints(int pointValue)
    {
    	totalPoints += pointValue;
    }
    //my idea here is that ship upgrades will cost points to buy, like in most games.
    public static void decreaseTotalPoints(int pointValue)
    {
    	if( (totalPoints - pointValue) >= 0)
    	{
    	    totalPoints -= pointValue;
    	}
    	else
    	    System.out.println("Can't spend any more points!"); //TODO Fix error message
    }
  
}