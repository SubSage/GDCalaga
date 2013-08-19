package org.gdc.gdcalaga;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.gdc.gdcalaga.audio.AudioAsset;
import org.gdc.gdcalaga.audio.AudioManager;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.geom.Vector2f;


public class PlayState extends BasicGameState {
	
    public static final int ID = 0;
    
    private Player player;


    private boolean paused = false;
    private boolean gameInProgress = false;
    
    private EntityManager entities = new EntityManager();
    private Input input;

    private AudioManager audioManager = AudioManager.getAudioManager();
    private HeadsUpDisplay hud;
    
    private List<DisplayObject> disObjs = new LinkedList<DisplayObject>();
    
    PathRegistry paths;
    
    //
    Vector2f startPosition = new Vector2f(50, 300);
    
    @Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
        paths = new PathRegistry();
        paths.loadFromJson("./data/paths.json");        
    	//initializeState(container);
	}
    
    private void initializeState(GameContainer container)
    {
    	//remove all active entities.
        for(Entity entity : entities.getEntities())
        {
        	entities.RemoveEntity(entity);
        }
    	
    	input = container.getInput();
    	player= new Player(entities, startPosition);
    	Spawn.resetSpawner(entities);
        Enemy.player = player;
        hud = new HeadsUpDisplay(player);
        disObjs.clear();
        
        try {
			audioManager.loadAudioAssets();
			audioManager.playMusic(AudioAsset.MUSIC_LEVEL_1);
			disObjs.add(new Background(container.getWidth(), container.getHeight()));
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        

    }
    
    public void restartState(GameContainer container)
    {
    	initializeState(container);
    }
    
    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
    	gameInProgress = true;
    }
    
    
    @Override
    public void leave(GameContainer container, StateBasedGame game) throws SlickException {
    	
    }
    
    
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		for (DisplayObject obj : disObjs) {
        	obj.draw(g);
        }
		
		entities.draw(g);
		
		if(paused)
		{
			
		}
		else
		{
			hud.render(g);
		}		
		renderDebugText(g);
	}


	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		
		if(paused)
		{
			return;
		}
		
		Collision.checkCollisions(entities.getEntities());
		
		Spawn.spawnWave(paths, entities);
		entities.update(delta);
		
		if( ( Math.random()*100) < .1  )
		{
			((Background)disObjs.get(0)).addRandomFloater();
		}
		
		shipInput(delta);
       
		for (DisplayObject obj : disObjs) {
			obj.update(delta);
		}
		
		if(player.IsDying())
        {
			gameInProgress = false;
			game.enterState(PostGameState.sceneId);
        }
        
        if(input.isKeyPressed(Input.KEY_ESCAPE))
        {
        	game.enterState(MainMenuState.stateId);
        }
        
		/* will put this back in later
        if(Input.WasKeyPressed(KeyEvent.VK_H))
        {
            screenShot();
        }
        */
	}

	
	
	

	private void renderDebugText(Graphics g) {
		/* If we use this same monospace font, each letter takes up 10 pixels
		 * so we can space the text according to this metric
		 */
		final int desiredSpacing = 15;    //Spacing in pixels between each statistic
		
		String playerHP = new String("Player HP: " + player.getHealth());
		int xPixel = 5;
        g.drawString(playerHP, xPixel, 30);
        
        String shieldsLeft = new String("Player Shields: " + player.getShields()/1000.0);
        xPixel += (playerHP.length() * 10) + desiredSpacing;
        g.drawString(shieldsLeft, xPixel, 30);
        
        String waveCount = new String("Round: " + Spawn.getWave());
        xPixel += (shieldsLeft.length() * 10) + desiredSpacing;
        g.drawString(waveCount, xPixel, 30);
        
        String pointCount = new String("Points " + Player.getTotalPoints());
        xPixel += (waveCount.length() * 10) + desiredSpacing;
        g.drawString(pointCount, xPixel, 30);
	}
	

	private void shipInput(int delta) {
		
		if(input.isKeyDown(Input.KEY_W)) {
		    player.moveUp(delta);
		}
		else if (input.isKeyDown(Input.KEY_S)) {
			player.moveDown(delta);
		}

		if(input.isKeyDown(Input.KEY_A)){
		    player.moveLeft(delta);
		}
		else if (input.isKeyDown(Input.KEY_D)){
		    player.moveRight(delta);
		}

		if(input.isKeyDown(Input.KEY_SPACE))
		{
		    if (player.fire(delta))
		    {
		        audioManager.playSFX(AudioAsset.SFX_FIRE1);
		    }
		}
		
		if(input.isKeyDown(Input.KEY_Q))
		{
		    if (player.activateShield(delta))
		    {
		        //audioManager.playSFX(AudioAsset.SFX_SHIELD1);
		        //should we play a sound, or just show a graphic?
		    }
		}
		else
		{
		    player.deactivateShield();
		}
	}
	
	@Override
	public int getID() {
		return ID;
	}
	
	public boolean isGameInProgress() {
		return gameInProgress;
	}
}
