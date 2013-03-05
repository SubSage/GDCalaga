import org.newdawn.slick.*;


public class Enemy extends Entity
{
    private float x, y, lastX, lastY, xVel, yVel, health;
    private int height, width, alliance;
    Image ship;
    private Explosion exp;
    private boolean exploding;
    
    public Enemy(EntityManager manager, int xpos, int ypos)
    {
        super(manager);
        x=xpos;lastX=x;
        y=ypos;lastY=y;
        width=50;
        height=50;
        xVel=0;
        yVel=100;
        health=3;
        alliance=0;
        shape = new RectShape(x, y, width, height);
        
        

        try {
			ship = new Image("Pics/BlueSquaretrans.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
        
        
    }
    
    
    public void update(float delta)
    {
        
        if(exploding)
        {
            exp.update(delta);
            if(exp.IsDead()) Destroy();
        } else {
            x+= xVel * delta / 1000;
            y+= yVel * delta / 1000;
            
            if(y<0){
                y=0;
                yVel*=-1;
            }
            
            if(y>720){
                y=720;
                yVel*=-1;
            }
            
            if(Math.random()*100*delta < 100){
                fire();
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
        
    }
    
    public void fire()
    {
        Bullet newBullet = new Bullet(entities, (int)x, (int)y ,1 , alliance);
        newBullet.setSpeed(-250, 0);
    }
    
    private void Explode(){
    	shape.type = Shape.ShapeType.Null;

        exp = new Explosion(x, y, 25, 25, width, height);
        exp.SetImage(ship);
        
        exploding = true;
        
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