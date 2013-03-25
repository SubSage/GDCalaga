package org.gdc.gdcalaga;

import org.gdc.gdcalaga.audio.AudioAsset;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
//Testing class. Making sure we can implement new enemies. Works fine
//Just add this guy instead of Enemy and you can see it work.
public class Enemy2 extends Enemy 
{
	private static final int SIZE_WIDTH = 73;
	private static final int SIZE_HEIGHT = 43;

	public Enemy2(EntityManager manager, Vector2f position) {
		super(manager, xpos, ypos);
		width=73;
		height=43;
		
		try {
			ship = new Image("Pics/EnemyShip2.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
	}
	
	public void fire()
	{
		Bullet newBullet = new Bullet(entities, (int)x - width/2 + 3, (int)y +height/2 ,1 , alliance);
		Bullet newBullet2 = new Bullet(entities, (int)x - width/2 + 3, (int)y -height/2,1 , alliance);
        newBullet.setSpeed(-250, 0);
        newBullet2.setSpeed(-250, 0);
        audioManager.playSFX(AudioAsset.SFX_FIRE2);
	}

}
