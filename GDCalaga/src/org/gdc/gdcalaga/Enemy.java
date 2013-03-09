package org.gdc.gdcalaga;
import java.util.Random;

import org.gdc.gdcalaga.audio.AudioAsset;
import org.gdc.gdcalaga.audio.AudioManager;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class Enemy extends Entity
{
	private static final int MAX_FIRE = 1500;
	
    private float x, y, lastX, lastY, xVel, yVel, health;
    private int height, width, alliance, pointValue;
    Image ship;
    private Explosion exp;
    private boolean exploding, pathing;
    
    private Path path;
    private PathNode node;
    private float startX, startY, pathXVel, pathYVel;
    private float fireRate;
    private float fireRateDT;
    private Random rand;
    
    private EnemyGroup group;
    private boolean grouped;
    public float relX, relY;

	private AudioManager audioManager;
    
    public Enemy(EntityManager manager, float xpos, float ypos)
    {
        super(manager);
        x=xpos;lastX=x;
        y=ypos;lastY=y;
        width=41;
        height=32;
        xVel=0;
        yVel=100;
        health=3;
        alliance=0;
        pointValue = 10;
        shape = new RectShape(x, y, width, height);
        
        pathing = false;
        grouped = false;
        relX = 0;
        relY = 0;
        
        rand = new Random(System.currentTimeMillis());
        fireRate = rand.nextInt(MAX_FIRE);
        fireRateDT = 0;

        try {
			ship = new Image("Pics/Enemy.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
        
        audioManager = AudioManager.getAudioManager();
    }
    
    public void setPath(Path newPath){
    	path = newPath;
    	
    	//Spawn at the first node in the path
    	PathNode spawnPos = path.next();
    	x = spawnPos.goalX;
    	y = spawnPos.goalY;
    	
    	//Then start moving toward the next node
    	if(path.hasNext()){
	    	node = path.next();
	    	startX = x;
	    	startY = y;
	    	pathing = true;
    	}
    }
    
    public void setGroup(EnemyGroup g, float x, float y){
    	group = g;
    	grouped = true;
    	relX = x;
    	relY = y;
    	x = relX + group.xPos;
    	y = relY + group.yPos;
    	g.addEnemy(this);
    }
    
    
    public void update(float delta)
    {
        
        if(exploding)
        {
            exp.update(delta);
            if(exp.IsDead()) Destroy();
        } else {
        	
        	fireRateDT += delta;
        	if (fireRateDT > fireRate) {
                fire();
                fireRateDT = 0;
                fireRate = rand.nextInt(MAX_FIRE);
            }
        	

        	
        	
        	if(!pathing){
        	    if(grouped){
                    y = group.yPos + relY;
                    x = group.xPos + relX;
                } else {
    	            x+= xVel * delta / 1000;
    	            y+= yVel * delta / 1000;
    	            
    	            if(y<0 || y>720){
    	            }
    
    	            if(y<0){
    	            	y = 0;
    	                yVel*=-1;
    	            }
    	            
    	            if(y>720){
    	            	y = 720;
    	                yVel*=-1;
    	            }
                }
        	} else {
                
				pathXVel = (node.goalX - startX) / node.speed;
				pathYVel = (node.goalY - startY) / node.speed;
        		x += pathXVel * delta / 1000;
        		y += pathYVel * delta / 1000;
        		if(pathXVel * (node.goalX - x) < 0 || pathYVel * (node.goalY - y) < 0){
        			x = node.goalX;
        			y = node.goalY;
        			startX = x;
        			startY = y;
        			if(path.hasNext()){
        				node = path.next();
        				if(grouped && !path.hasNext()){
        				    node.goalX = group.xPos + relX;
        				    node.goalY = group.getYAfterTime(node.speed) + relY;
        				}
        			} else {
        				pathing = false;
        			}
        		}
        		
        		
        	}
            
            
        }
        
        
        RectShape rect = (RectShape)shape;
        rect.xpos = x;
        rect.ypos = y;
    }
    
    
    
    public void draw(Graphics g)
    {

        int drawX = (int) (x - width/2);
        int drawY = (int) (y - height/2);
        float scale = width / ship.getWidth();
        if(!exploding){
        	ship.draw(drawX, drawY, scale, Color.white);
        } else {
        	exp.render(g);
        }
    	
    }

    public void Collide(Entity other)
    {
    	if(other instanceof Bullet && ((Bullet)other).getAlliance()!=alliance)
    	{
    		Hurt(((Bullet)other).getDamage());
    	}
        
    }
    
    public void fire()
    {
        Bullet newBullet = new Bullet(entities, (int)x - width/2 + 3, (int)y ,1 , alliance);
        newBullet.setSpeed(-250, 0);
        audioManager.playSFX(AudioAsset.SFX_FIRE2);
    }
    
    private void Explode(){
    	shape.type = Shape.ShapeType.Null;

        exp = new Explosion(x, y, 41, 8, width, height);
        exp.SetImage(ship);
        
        exploding = true;    
        
        if(grouped) group.removeEnemy(this);
        Player.increaseTotalPoints(pointValue);
    }
    
    
    public void Hurt(float dmg)
    {
        health -= dmg;
        if(health<=0) Explode();
    }
    
    public int getAlliance(){
        return alliance;
    }

}