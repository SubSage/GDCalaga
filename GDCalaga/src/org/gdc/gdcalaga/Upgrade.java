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
        //Droppable upgrades
        HEALTH,
        SHIELD,
        FIRE_RATE,
        NUM_GUNS,
        
        //Buyable upgrades
        SPEED
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
        vel.set(-SPEED, 0);
        size.set(SIZE_WIDTH, SIZE_HEIGHT);
		
        shape = new RectShape(pos, size);
        
        upgradeType = type;
        
        // TODO Instantiate the image depending on what upgrade type this is
        /*try {
            image = new Image("Pics/...");
        } catch (SlickException e) {
            e.printStackTrace();
        }*/
    }
    
    public void update(float delta)
    {
        //TODO Temporary right to left linear movement, perhaps sinusoidal movement?
        this.pos.x += vel.x * delta / 1000;
        
        RectShape rect = (RectShape)shape;
        rect.pos.set(this.pos);
    }

    public void draw(Graphics g) {
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
