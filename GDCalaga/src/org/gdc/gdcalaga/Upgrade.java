package org.gdc.gdcalaga;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

/*
 * Upgrade class. Upgrades are dropped randomly when enemies 
 * die, and the player must pickup the upgrade to receive the upgrade.
 * Change this class to just an Upgrade class, that only collides with the player
 * to activate an upgrade() function
 */

/* TODO
 * Set up an RNG for enemies to drop these upgrades
 * Make an image for each of the upgrades we plan to have, preferably 
 *  all the same dimensions
 * Make a path for the upgrade to travel along
 */

public class Upgrade extends Entity
{
    public enum UpgradeType
    {
        /*
         *  A new image must be created for a new upgrade
         */
    	
        HEALTH (new String("Pics/upgrade_health.png")),
        SHIELD (new String("Pics/upgrade_shield.png")),
        FIRE_RATE (new String("Pics/upgrade_firerate.png")),
        NUM_GUNS (new String("Pics/upgrade_numguns.png")),
        DAMAGE (new String("Pics/upgrade_damage.png")),
        
        /* Error upgrade, should always be the last value in this enum 
           and is not included in upgrade count */
        INVALID_UPGRADE (new String("Pics/upgrade_invalid.png"));
        
        private String path;
        
        private UpgradeType(String path) {
        	this.path = path;
        }
        
        public String getImagePath() {
        	return path;
        }
        
        /**
         * Returns the number of upgrades.
         */
        public static int getUpgradeCount() {
        	return values().length;
        }
        
        /**
         * Indices start at zero and are ordered as listed.
         */
        public static UpgradeType getIndexedUpgrade(int index) {
        	if (index < values().length)
        		return values()[index];
        	else
        		return INVALID_UPGRADE;
        }
        
    }
    
    private static final float SPEED = 50.f;
    //TODO Change the size values when the upgrade image is made
    private static final float SIZE_WIDTH = 25.f;
    private static final float SIZE_HEIGHT = 25.f;
	
    public Vector2f initPos, vel;
    protected Image image;
	
    protected Path path;
    protected PathNode node;
    private int amplitude;
    
    public Upgrade.UpgradeType upgradeType;
	
    public Upgrade(EntityManager manager, Vector2f initPos, Upgrade.UpgradeType type) 
    {
        super(manager);
        
        alliance = Entity.Alliance.UPGRADE;
        
        pos = new Vector2f(initPos);
        this.initPos = new Vector2f(initPos);
        vel = new Vector2f(-SPEED, 0);
        size = new Vector2f(SIZE_WIDTH, SIZE_HEIGHT);
		
        shape = new RectShape(pos, size);
        
        upgradeType = type;
        
        try {
            image = new Image(type.getImagePath());
        } catch (SlickException e) {
            e.printStackTrace();
        }
        
       //Initialize a random amplitude for the movement function
        Random rand = new Random(System.currentTimeMillis());
        int maxAmplitude = 50;
        int minAmplitude = 10;
        amplitude = rand.nextInt(maxAmplitude - minAmplitude + 1) + minAmplitude;
    }
    
    public void update(float delta)
    { 
        this.pos.x += vel.x * delta / 1000;
        this.pos.y = (float)(amplitude * Math.sin(pos.x / 20)) + initPos.y;
        if (this.pos.x <= -20)
        {
            Destroy();
        }
       
        RectShape rect = (RectShape)shape;
        rect.pos.set(this.pos);
    }

    public void draw(Graphics g) 
    {
        int drawX = (int)(pos.x - size.x / 2);
        int drawY = (int)(pos.y - size.y / 2);
        float scale = size.x / image.getWidth();
        image.draw(drawX, drawY, scale, Color.white);
    }

    public void Collide(Entity other) 
    {
        if(other instanceof Player)
        {
            Player.upgrade(upgradeType);
            Destroy();
        }
    }
}
