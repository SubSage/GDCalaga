package org.gdc.gdcalaga;

import org.gdc.gdcalaga.audio.AudioAsset;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
//Testing class. Making sure we can implement new enemies. Works fine
//Just add this guy instead of Enemy and you can see it work.
public class Enemy2 extends Enemy 
{
    private static final int SIZE_WIDTH = 73;
    private static final int SIZE_HEIGHT = 43;
    private static final int BULLET_SPEED = 250;

    public Enemy2(EntityManager manager, Vector2f position) {
        super(manager, position);
        size = new Vector2f(SIZE_WIDTH, SIZE_HEIGHT);
		
        try {
            ship = new Image("Pics/EnemyShip2.png");
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
	
	public void fire()
	{
	    Vector2f bulletPos = new Vector2f((int)(pos.x - size.x / 2) + 3, (int)(pos.y + size.y / 2));
	    Bullet newBullet = new Bullet(entities, bulletPos, 1, alliance);
	    
	    bulletPos.set((int)(pos.x - size.x / 2) + 3, (int)(pos.y - size.y / 2));
	    Bullet newBullet2 = new Bullet(entities, bulletPos, 1, alliance);
	    
        newBullet.setSpeed(-BULLET_SPEED, 0);
        newBullet2.setSpeed(-BULLET_SPEED, 0);
        audioManager.playSFX(AudioAsset.SFX_FIRE2);
    }
}
