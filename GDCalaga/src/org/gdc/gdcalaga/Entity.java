import org.newdawn.slick.*;


//Entity class. An entity is anything that moves and shoots. Main "thing" the player plays against.
//The player is also an entity. interp is the time inteveral, so that we can update the entity according to time
//alliance to detect which entities are against each other....so far it should only be player vs enemes....but who knows

public abstract class Entity
{
    
    public int id;
    protected EntityManager entities;
    protected float x, y, width, height, alliance, interp;
    private boolean dying;
    
    protected Shape shape;
    
    
    public Entity(EntityManager manager)
    {
        entities = manager;
        entities.AddEntity(this);
        
        dying = false;
        x=y=alliance=0;
    }

    public Entity(int xpos, int ypos)
    {
        x=xpos;
        y=ypos;//Thinking of things, x and xpos should be switched, but the convenience....
    }
    
    public Entity(int xpos, int ypos, float w, float h)
    {
        x=xpos;
        y=ypos;//Thinking of things, x and xpos should be switched, but the convenience....
        width = w;
        height = h;
        alliance=0;
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