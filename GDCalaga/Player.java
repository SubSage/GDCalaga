import org.newdawn.slick.*;



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
        width=25;
        height=25;
        xVel=20;
        yVel=20;
        health=10;
        alliance=1;
        
        shape = new RectShape(x, y, width, height);
        

        try {
			ship= new Image("Pics/BlueSquare.png");
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
        ship.draw(drawX, drawY, scale);
    }
    
    
    public void moveUp()
    {
        y-=yVel;
    }
    
    public void moveDown()
    {
        y+=yVel;
    }
    
    public void moveLeft()
    {
        x-=xVel;
    }
    
    public void moveRight()
    {
        x+=xVel;
    }
    
    public void fire()
    {
        Bullet newBullet = new Bullet(entities, (int)x, (int)y ,1 , alliance);
        newBullet.setSpeed(250, 0);
    }
    
    public void Collide(Entity other)
    {
        
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