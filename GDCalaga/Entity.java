import java.awt.*;


//Entity class. An entity is anything that moves and shoots. Main "thing" the player plays against.
//The player is also an entity. interp is the time inteveral, so that we can update the entity according to time
//alliance to detect which entities are against each other....so far it should only be player vs enemes....but who knows

public abstract class Entity
{
    private float x, y, alliance, interp;
    
    public Entity()
    {
        x=y=alliance=0;
    }
    
    public Entity(int xpos, int ypos)
    {
        x=xpos;
        y=ypos;//Thinking of things, x and xpos should be switched, but the convenience....
        alliance=0;
    }
    
    public abstract void update(float delta);
    
    public abstract void draw(Graphics g, float interp);
    
    
    
    
}