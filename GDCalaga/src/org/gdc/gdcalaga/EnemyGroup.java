package org.gdc.gdcalaga;
import java.util.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class EnemyGroup extends Entity{
	public Vector2f pos, velocity;
	private float top, bottom;
	private ArrayList<Enemy> enemies;
	
	public EnemyGroup(EntityManager mgr, float vely, float x, float y){
		super(mgr);
		velocity = new Vector2f(0, vely);
		pos = new Vector2f(x, y);
		enemies = new ArrayList<Enemy>();
		
	}
	
	public void changeDir(){
		velocity.negateLocal();
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
		pos.y = y;
	}
	
	private void getBounds(){
	    top = 720;
	    bottom = 0;
	    for(Enemy e: enemies)
	    {
	        if (e.relPos.y < top)
	            top = e.relPos.y;
	        if (e.relPos.y > bottom)
	            bottom = e.relPos.y;
	    }
	}
	
	public float getYAfterTime(float time){
	    float dy = pos.y + velocity.y / time;
	    if(dy + bottom > 720) dy = 720 - bottom + 720 - bottom - dy;
	    if(dy + top < 0) dy = 0 - top - dy;
	    return dy;
	}

	public void update(float delta) {
		pos.y += velocity.y * delta / 1000;
		
		if(pos.y + top < 0){
		    pos.y = 0 - top;
            changeDir();
		}
		
		if(pos.y + bottom > 720){
		    pos.y = 720 - bottom;
		    changeDir();
		}
		
	}

	public void draw(Graphics g) {
		
	}

	public void Collide(Entity other) {
	}
	
}
