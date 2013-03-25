package org.gdc.gdcalaga;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

/*
 * DroppableUpgrade class. DroppableUpgrades are dropped randomly when enemies 
 * die, and the player must pickup the upgrade to receive the upgrade.
 */


public abstract class DroppableUpgrade extends Entity 
{
    private static final float SPEED = 50.f;
    private static final float SIZE = 25.f;
	
    protected Vector2f pos, vel, size;
    protected Shape shape;
    protected Image image;
	
    protected Path path;
    protected PathNode node;
	
    public DroppableUpgrade(EntityManager manager, Vector2f initPos) 
    {
        super(manager);
        
        pos = initPos;
        vel.set(-SPEED, 0);
        size.set(SIZE, SIZE);
		
        shape = new RectShape(pos, size);
    }
}
