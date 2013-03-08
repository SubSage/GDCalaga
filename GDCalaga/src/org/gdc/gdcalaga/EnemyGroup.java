package org.gdc.gdcalaga;
import java.util.*;

import org.newdawn.slick.Graphics;

public class EnemyGroup extends Entity{
	public float xPos, yPos, yVel;
	private float top, bottom;
	private ArrayList<Enemy> enemies;
	
	public EnemyGroup(EntityManager mgr, float vely, float x, float y){
		super(mgr);
		yVel = vely;
		xPos = x;
		yPos = y;
		enemies = new ArrayList<Enemy>();
		
	}
	
	public void changeDir(){
		yVel *= -1;
	}
	
	public void addEnemy(Enemy enemy){
		enemies.add(enemy);
		getBounds();
	}
	
	public void removeEnemy(Enemy enemy){
		enemies.remove(enemy);
        getBounds();
	}
	
	public void setY(float y){
		yPos = y;
	}
	
	private void getBounds(){
	    top = 720;
	    bottom = 0;
	    for(Enemy e: enemies){
	        if(e.relY < top) top = e.relY;
	        if(e.relY > bottom) bottom = e.relY;
	    }
	}
	
	public float getYAfterTime(float time){
	    float dy = yPos + yVel / time;
	    if(dy + bottom > 720) dy = 720 - bottom + 720 - bottom - dy;
	    if(dy + top < 0) dy = 0 - top - dy;
	    return dy;
	}

	public void update(float delta) {
		yPos += yVel * delta / 1000;
		
		if(yPos + top < 0){
		    yPos = 0 - top;
            changeDir();
		}
		
		if(yPos + bottom > 720){
		    yPos = 720 - bottom;
		    changeDir();
		}
		
		if(enemies.size() == 0){
			Destroy();
		}
	}

	public void draw(Graphics g) {
		
	}

	public void Collide(Entity other) {
	}
	
}
