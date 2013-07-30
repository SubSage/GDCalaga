package org.gdc.gdcalaga;

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
	
	public Gun(EntityManager entityManager, float velocity, Entity.Alliance alliance, GunType gunType)
	{
		this.entityManager = entityManager;
		this.velocity = velocity;
		this.alliance = alliance;
		this.gunType = gunType;
		
	}
	
	public void shoot(Vector2f bulletPosition, Player player)
	{
    	switch(gunType)
    	{
    	case StraightShot:
    		straightShot(bulletPosition);
    		break;
    	case AimedShot:
    		aimedShot(bulletPosition, player);
    		break;
    	case TripleShot:
    		tripleShot(bulletPosition);
    		break;
		default: 
			break;
    	}
	}
	
	private void straightShot(Vector2f bulletPosition) {
        Bullet newBullet = new Bullet(entityManager, bulletPosition, 1, alliance);        
        newBullet.setSpeed(-250, 0);
	}
	
	private void aimedShot(Vector2f bulletPosition, Player player) {
        
        Bullet newBullet = new Bullet(entityManager, bulletPosition, 1, alliance);
        
        Vector2f directionOfPlayer = new Vector2f(player.getPosition());
        directionOfPlayer.x -= bulletPosition.x;
        directionOfPlayer.y += (-bulletPosition.y) + (Math.random() * 20) - 10;
        directionOfPlayer.normalise();
        
        newBullet.setSpeed(directionOfPlayer.x * 250,directionOfPlayer.y  * 250);
	}
	
	private void tripleShot(Vector2f bulletPosition){
	
		Bullet diagonalUpBullet = new Bullet(entityManager, bulletPosition, 1, alliance);
		Vector2f diagonalUp = new Vector2f(-2,1);
		diagonalUp.normalise();
		diagonalUpBullet.setSpeed(diagonalUp.x * velocity, diagonalUp.y * velocity);
        
        straightShot(bulletPosition);
        
        Vector2f diagonalDown = new Vector2f(-2,-1);
		diagonalDown.normalise();
        Bullet diagonalDownBullet = new Bullet(entityManager, bulletPosition, 1, alliance);        
        
        diagonalDownBullet.setSpeed(diagonalDown.x * velocity, diagonalDown.y * velocity);
	}
}
