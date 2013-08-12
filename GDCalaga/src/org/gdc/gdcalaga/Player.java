package org.gdc.gdcalaga;
import org.gdc.gdcalaga.Gun.GunType;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

/*
 * Upgrades planned for the player:
 * Droppable: Health, shields, fire rate, more bullets, damage
 * Buyable: Speed 
 * 
 * TODO
 * Store the upgrades in an easily retrievable, searchable, and identifiable manner
 *  We want to have levels of upgrades, so this should be considered
 * Create a shop at the end of a wave/stage (or something like that)
 *  for the player to buy upgrades.
 * Create functionality for the player to be able to activate shields/invuln mode
 * Create functionality for the player to have more guns
 */

public class Player extends Entity
{
    private static final int SIZE_WIDTH = 49;
    private static final int SIZE_HEIGHT = 29;
	
    private static int totalPoints = 0; //the score is static in case we give players multiple lives in the future
    Image ship;
    
    //Upgradable Player attributes
    private static float health;
    private static final float maxHealth = 10;
    private static float shields;
    private static float fireRate; //fireRate in bullets per second
    private static int numGuns;
    private static int lives;
    
    private Gun gun;
    private Explosion exp;
    protected Vector2f velocity;
    
    private static boolean shieldActivated;
    
    //these values are intertwined with fireRate to create a standard fire rate
    private static int ticksPerBullet;
    private int ticksSinceLastBullet;
    
    private GunPositions gunPositions;
        
    private boolean allowUserControl = true;
    private boolean flyIn = true;
    private boolean exploding = false;
    
    public Player(EntityManager manager, Vector2f position)
    {
        super(manager);

        pos.set(position);
        size = new Vector2f(SIZE_WIDTH, SIZE_HEIGHT);
        velocity = new Vector2f(220, 220);
        alliance = Alliance.FRIENDLY;
        
        health = 10;
        shields = 0;
        fireRate = 5;   //bullets per second
        numGuns = 1;
        lives = 3;
        ticksPerBullet = (int)(1000 / fireRate);   //milliseconds in a second / fireRate
                                                   //since delta time is in milliseconds
        ticksSinceLastBullet = ticksPerBullet;     //so we can shoot right off the bat
        
        shieldActivated = false;
        
        shape = new RectShape(pos, size);
        
        gunPositions = new GunPositions(SIZE_WIDTH, SIZE_HEIGHT);
        
        gun = new Gun(manager, 500, Entity.Alliance.FRIENDLY, GunType.StraightShot);
        
        try {
            ship= new Image("Pics/Player.png");
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
    
    
    public void update(float delta)
    {    	
		if(flyIn)
    	{
    		flyInBehaviour(delta);
    	}
    	else if(exploding)
        {
            exp.update(delta);
        }
    	else
    	{
	        ticksSinceLastBullet += delta;
	        if (shields <= 0)
	        {
	            deactivateShield();
	            shields = 0;
	        }
	        
	    	RectShape rect = (RectShape)shape;
	        rect.pos.set(this.pos);
    	}
        
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
    	if(flyIn)
    		return;
    	
    	pos.y -= velocity.y * (delta / 1000);
        pos.y = Math.max(size.y / 2, pos.y);
    }
    
    public void moveDown(float delta)
    {
    	if(flyIn)
    		return;
    	pos.y += velocity.y * (delta / 1000);
        pos.y = Math.min(720 - size.y / 2, pos.y);
    }
    
    public void moveLeft(float delta)
    {
    	if(flyIn)
    		return;
 	pos.x -= velocity.x * (delta / 1000);
        pos.x = Math.max(0 + size.x / 2, pos.x);
    }
    
    public void moveRight(float delta)
    {
    	if(flyIn)
    		return;
	pos.x += velocity.y * (delta / 1000);
        pos.x = Math.min(1280 - size.x / 2, pos.x);
    }
    
    public boolean fire(float delta)
    {
    	if(flyIn)
    		return false;
    	
        //TODO add more firing positions and more bullets depending on the upgrades
        if (ticksSinceLastBullet >= ticksPerBullet)
        {
            int max = numGuns < gunPositions.getSize() ? numGuns : gunPositions.getSize();
            for (int i = 0; i < max; i++)
            {
            	Vector2f shotOrigin = new Vector2f(gunPositions.getPosition(i));
            	gun.shoot(shotOrigin.add(pos), gunPositions.getPosition(i));
            }
            ticksSinceLastBullet = 0;
            return true;
        }
        else 
        {
            return false;
        }
            
    }
    
    public boolean activateShield(float delta)
    {
    	if(!allowUserControl)
    		return false;
    	
        if (shields > 0)
        {
            shieldActivated = true;
            shields -= delta;
            return true;
        }
        else
        {
            deactivateShield();
            shields = 0;
            return false;
        }
    }
    
    public void deactivateShield()
    {
        shieldActivated = false;
    }
    
    public void Collide(Entity other)
    {
    	if(flyIn)
    		return;
    	
    	if(other instanceof Bullet && ((Bullet)other).getAlliance() != alliance
    			|| other instanceof Enemy)
    	{
    	    Hurt(((Entity)other).getCollisionDamage());
    	}
    }
    
    public void Hurt(float dmg)
    {
        if (shieldActivated)
            return;
        
        health -= dmg;
        
        if(health <= 0)
        {
        	--lives;
        	if(lives < 0)
        	{
        		Explode();
        		Destroy();
    		}
        	else
        	{
        		Explode();
        		pos = new Vector2f(-size.x, GDCalaga.SCREEN_SIZE_Y / 2);
        		allowUserControl = false;
        		flyIn = true;
        		health = maxHealth;
        	}	
	}        		
    }
    
    public float getHealth()
    {
        return health;
    }
    
    public float getShields()
    {
        return shields;
    }
    
    public Entity.Alliance getAlliance()
    {
        return alliance;
    }
    
    public Vector2f getPosition()
    {
    	return pos;
    }
    
    public int getLives()
    {
    	return lives;
    }
    
    public Gun getWeapon() {
    	return gun;
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
    	if(totalPoints - pointValue >= 0)
    	    totalPoints -= pointValue;
    	else
    	    System.out.println("Can't spend any more points!"); //TODO Fix error message
    }
    
    public static void upgrade(Upgrade.UpgradeType upgrade)
    {
        //TODO Change the ealth, hp/armor, fire rate, amount of guns, speed
        //according to the upgrade passed in
    	switch (upgrade)
        {
        case HEALTH:
            health += 5;
            break;
        case SHIELD:
            shields += 3000;    //Give 3 seconds worth of shielding
            break;
        case FIRE_RATE:
            fireRate += .5;      //Having these two variables separated makes it easier to debug and read
            ticksPerBullet = (int)(1000 / fireRate); //A little bit weird that we have to do these two things...
            break;
        case NUM_GUNS:
            numGuns++;
            break;
        case DAMAGE:
            //damage++;
            break;
        case INVALID_UPGRADE:
            break;
        default:
            break;  
        }
    }
    
    
    private void flyInBehaviour(float delta)
    {
    	
		pos.x += velocity.y * (delta * 2 / 1000);        
    	if(pos.x >= 300)
    	{
    		flyIn = false;
    		
    	}
    }
    
    private void Explode()
    {
        //exp = new Explosion(pos, 41, 8, size);
        //exp.SetImage(ship);
        //exploding = true;    
    }
    
    
}
