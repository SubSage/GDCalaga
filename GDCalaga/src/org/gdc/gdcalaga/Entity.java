package org.gdc.gdcalaga;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

//Entity class. An entity is anything that moves and shoots. Main "thing" the player plays against.
//The player is also an entity. interp is the time inteveral, so that we can update the entity according to time
//alliance to detect which entities are against each other....so far it should only be player vs enemes....but who knows

public abstract class Entity
{
    public int id;
    protected EntityManager entities;
    protected float alliance, interp;
    protected Vector2f pos, size;
    private boolean dying;
    
    protected Shape shape;
    
    
    public Entity(EntityManager manager)
    {
        entities = manager;
        entities.AddEntity(this);
        
        dying = false;
        alliance = 0;
        pos.set(0, 0);
    }

    public Entity(int xpos, int ypos)
    {
        pos.x = xpos;
        pos.y = ypos;
    }
    
    public Entity(int xpos, int ypos, float w, float h)
    {
        alliance = 0;
        
        pos.x = xpos;
        pos.y = ypos;
        size.x = w;
        size.y = h;
    }
    
    public Entity(Vector2f position, Vector2f size)
    {
    	this.pos = position;
    	this.size = size;
    }
    
    public boolean IsDying()
    {
        return dying;
    }
    
    public void Destroy()
    {
        dying = true;
    }
    
    public abstract void update(float delta);
    
    public abstract void draw(Graphics g);
    
    public abstract void Collide(Entity other);
    
    
}