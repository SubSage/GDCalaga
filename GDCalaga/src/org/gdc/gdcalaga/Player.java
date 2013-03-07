package org.gdc.gdcalaga;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;



public class Player extends Entity
{
    private float xVel, yVel, health;
    private int height, width, alliance;
    Image ship;
    
    public Player(EntityManager manager, int xpos, int ypos)
    {
        super(manager);
        x=xpos;
        y=ypos;
        width=49;
        height=29;
        xVel=220;
        yVel=220;
        health=10;
        alliance=1;
        
        shape = new RectShape(x, y, width, height);
        

        try {
			ship= new Image("Pics/Player.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
    }
    
    
    public void update(float delta)
    {
    	RectShape rect = (RectShape)shape;
        rect.xpos = x;
        rect.ypos = y;
    }
    
    public void draw(Graphics g)
    {
        int drawX = (int) (x - width/2);
        int drawY = (int) (y - height/2);
        float scale = width / ship.getWidth();
        ship.draw(drawX, drawY, scale, Color.white);
    }
    
    
    public void moveUp(float delta)
    {
        y-=yVel*delta/1000;
        y = Math.max(height/2, y);
    }
    
    public void moveDown(float delta)
    {
        y+=yVel*delta/1000;
        y = Math.min(720 - height/2, y);
    }
    
    public void moveLeft(float delta)
    {
        x-=xVel*delta/1000;
        x = Math.max(0 + width/2, x);
    }
    
    public void moveRight(float delta)
    {
        x+=xVel*delta/1000;
        x = Math.min(1280 - width/2, x);
    }
    
    public void fire()
    {
        Bullet newBullet = new Bullet(entities, (int)x + width/2, (int)y ,1 , alliance);
        newBullet.setSpeed(500, 0);
    }
    
    public void Collide(Entity other)
    {
    	if(other instanceof Bullet && ((Bullet)other).getAlliance()!=alliance)
    	{
    		Hurt(((Bullet)other).getDamage());
    	}
    }
    
    public void Hurt(float dmg){
        health-=dmg;
    }
    
    public int getHealth(){
        return (int)health;
    }
    
    public int getAlliance(){
        return alliance;
    }
}