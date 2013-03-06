package org.gdc.gdcalaga;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class PlayState extends BasicGameState {
	public static final int ID = 0;

    private boolean paused = false;
    private Player player;

    private EntityManager entities = new EntityManager();
    private Input input;

    @Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
        input = container.getInput();
        player= new Player(entities,50,300);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		entities.draw(g);
        g.drawString("Player HP: " + player.getHealth(), 5, 30);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		if(!paused){
	        Collision.checkCollisions(entities.getEntities());
	        Spawn.spawnWave(entities.getEntities(), entities);
            entities.update(delta);
            
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

	@Override
	public int getID() {
		return ID;
	}
}
