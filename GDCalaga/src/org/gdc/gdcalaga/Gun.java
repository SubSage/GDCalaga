package org.gdc.gdcalaga;

import org.gdc.gdcalaga.Entity.Alliance;
import org.newdawn.slick.geom.Vector2f;


public class  Gun {

	public static int MAX_GUN_TYPES = 3; //fixme use enum max value correctly.
	
	enum GunType {
		StraightShot,
		AimedShot,
		TripleShot,
	}
	
	protected float velocity;
	protected GunType gunType;
	protected EntityManager entityManager;
	protected Entity.Alliance alliance;
	protected int bulletDamage = 1;
	
	
	public Gun(EntityManager entityManager, float velocity, Entity.Alliance alliance, GunType gunType)
	{
		this.entityManager = entityManager;
		this.velocity = velocity;
		this.alliance = alliance;
		this.gunType = gunType;
		
	}
	
	public void shoot(Vector2f bulletPosition, Vector2f target)
	{
    	switch(gunType)
    	{
    	case StraightShot:
    		straightShot(bulletPosition);
    		break;
    	case AimedShot:
    		aimedShot(bulletPosition, target);
    		break;
    	case TripleShot:
    		tripleShot(bulletPosition);
    		break;
		default: 
			break;
    	}
	}
	
	private void straightShot(Vector2f bulletPosition) {
        Bullet newBullet = new Bullet(entityManager, bulletPosition, bulletDamage, alliance);
        if(alliance == Alliance.ENEMY)
        	newBullet.setSpeed(-250, 0);
        else
        	newBullet.setSpeed(250, 0);
	}
	
	private void aimedShot(Vector2f bulletPosition,  Vector2f target) {
        
        Bullet newBullet = new Bullet(entityManager, bulletPosition, bulletDamage, alliance);
        
        // change the x direction to point towards target, 
        // add random y direction to make the shot seem not 100% accurate.
        Vector2f directionOfTarget = new Vector2f(target);  
        directionOfTarget.x -= bulletPosition.x;
        directionOfTarget.y += (-bulletPosition.y) + (Math.random() * 20) - 10;
        directionOfTarget.normalise();
        
        newBullet.setSpeed(directionOfTarget.x * 250, directionOfTarget.y  * 250);
	}
	
	private void tripleShot(Vector2f bulletPosition){
	
		Bullet diagonalUpBullet = new Bullet(entityManager, bulletPosition, bulletDamage, alliance);
		Vector2f diagonalUp = new Vector2f(-2,1);
		diagonalUp.normalise();
		diagonalUpBullet.setSpeed(diagonalUp.x * velocity, diagonalUp.y * velocity);
        
        straightShot(bulletPosition);
        
        Vector2f diagonalDown = new Vector2f(-2,-1);
		diagonalDown.normalise();
        Bullet diagonalDownBullet = new Bullet(entityManager, bulletPosition, bulletDamage, alliance);        
        
        diagonalDownBullet.setSpeed(diagonalDown.x * velocity, diagonalDown.y * velocity);
	}
	
	public String getName() {
		return gunType.toString();
	}
}
