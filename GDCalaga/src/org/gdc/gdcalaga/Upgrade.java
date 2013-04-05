package org.gdc.gdcalaga;

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
        /* Whenever upgrades are added or removed, 
         *  NUM_UPGRADES must be updated
         *  getIndexedUpgrade() must be updated
         *  the constructor of Upgrade() must be updated
         *  A new image must be created for a new upgrade
         */
        
        //Droppable upgrades
        HEALTH,
        SHIELD,
        FIRE_RATE,
        NUM_GUNS,
        DAMAGE,
        
        //Buyable upgrades
        SPEED,
        
        /* Error upgrade, should always be the last value in this enum 
           and is not included in upgrade count */
        INVALID_UPGRADE;
        
        //This number MUST be changed whenever an upgrade is added here
        private static final int NUM_DROPPABLE_UPGRADES = 5;
        
        public static int getNumDroppableUpgrades()
        {
            return NUM_DROPPABLE_UPGRADES;
        }
        
        //This method MUST be updated to include changes to the types
        public static UpgradeType getIndexedUpgrade(int index)
        {
            //Droppable upgrades always go first
            //When in doubt, use the same order as in the enum
            switch (index)
            {
            case 1:
                return UpgradeType.HEALTH;
            case 2:
                return UpgradeType.SHIELD;
            case 3:
                return UpgradeType.FIRE_RATE;
            case 4:
                return UpgradeType.NUM_GUNS;
            case 5:
                return UpgradeType.DAMAGE;
            case 6:
                return UpgradeType.SPEED;
            default:
                return UpgradeType.INVALID_UPGRADE;
            }
        }
    }
    
    private static final float SPEED = 50.f;
    //TODO Change the size values when the upgrade image is made
    private static final float SIZE_WIDTH = 25.f;
    private static final float SIZE_HEIGHT = 25.f;
	
    public Vector2f pos, vel, size;
    protected Shape shape;
    protected Image image;
	
    protected Path path;
    protected PathNode node;
    
    public Upgrade.UpgradeType upgradeType;
	
    public Upgrade(EntityManager manager, Vector2f initPos, Upgrade.UpgradeType type) 
    {
        super(manager);
        
        pos = new Vector2f(initPos);
        vel = new Vector2f(-SPEED, 0);
        size = new Vector2f(SIZE_WIDTH, SIZE_HEIGHT);
		
        shape = new RectShape(pos, size);
        
        upgradeType = type;
        String imageDirectory;
        //These upgrade types do not include Buyable upgrades
        switch (upgradeType)
        {
        case HEALTH:
            imageDirectory = new String("Pics/upgrade_health.png");
            break;
        case SHIELD:
            imageDirectory = new String("Pics/upgrade_shield.png");
            break;
        case FIRE_RATE:
            imageDirectory = new String("Pics/upgrade_firerate.png");
            break; 
        case NUM_GUNS:
            imageDirectory = new String("Pics/upgrade_numguns.png");
            break;
        case DAMAGE:
            imageDirectory = new String("Pics/upgrade_damage.png");
            break;
        case INVALID_UPGRADE:
            imageDirectory = new String("Pics/upgrade_invalid.png");
            break;
        default:
            imageDirectory = new String("Pics/upgrade_invalid.png");
            break;  
        }
        
        try {
            image = new Image(imageDirectory);
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
    
    public void update(float delta)
    {
        //TODO Temporary right to left linear movement, perhaps sinusoidal movement?
        this.pos.x += vel.x * delta / 1000;
        
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

    public void Collide(Entity other) {
        if(other instanceof Player)
        {
            Player.upgrade(upgradeType);
        }
    }
}
