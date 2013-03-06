import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class PlayState extends BasicGameState {
	public static int ID = 0;

    boolean paused = false;
    Player player;

    private EntityManager Entities = new EntityManager();
    private Input input;

	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {

        input = container.getInput();
        player= new Player(Entities,50,300);
		
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		Entities.draw(g);
        g.drawString("Player HP: " + player.getHealth(), 5, 30);
	}

	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		if(!paused){
	        Collision.CheckCollisions(Entities.GetEntities());
	        Spawn.spawnWave(Entities.GetEntities(),Entities);
            Entities.update(delta);
            
            if(input.isKeyDown(Input.KEY_W)){
                player.moveUp(delta);
            }
            if(input.isKeyDown(Input.KEY_S)){
                player.moveDown(delta);
            }
            if(input.isKeyDown(Input.KEY_A)){
                player.moveLeft(delta);
            }
            if(input.isKeyDown(Input.KEY_D)){
                player.moveRight(delta);
            }
            

            if(input.isKeyPressed(Input.KEY_SPACE))
            {
                player.fire();
            }
		}
		
        
        if(input.isKeyPressed(Input.KEY_ESCAPE))
        {
            paused=!paused;
        }
		

		/* will put this back in later
        if(Input.WasKeyPressed(KeyEvent.VK_H))
        {
            screenShot();
        }
        */
	}

	public int getID() {
		return ID;
	}

}
