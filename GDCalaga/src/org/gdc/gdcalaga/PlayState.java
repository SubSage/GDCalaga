package org.gdc.gdcalaga;
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

	private enum State {
		Playing,
		Pause,
		MainMenu,
		Highscore,
		PostGame,
	}
	
    public static final int ID = 0;
    
    private Player player;

    final int menuKeyDelay = 250; 
    int menuKeyDelayCounter = 250;
    
    private State state = State.MainMenu;
    boolean gameInProgress = false;
    
    private String playerName = "Player1"; //storing player entered name;
    
    private EntityManager entities = new EntityManager();
    private Input input;

    private AudioManager audioManager = AudioManager.getAudioManager();
    private HighscoreList highscoreList = new HighscoreList(GDCalaga.SCREEN_SIZE_X,GDCalaga.SCREEN_SIZE_Y);
    private Menu mainMenu = new Menu(GDCalaga.SCREEN_SIZE_X,GDCalaga.SCREEN_SIZE_Y);
    private HeadsUpDisplay hud;
    
    private List<DisplayObject> disObjs = new LinkedList<DisplayObject>();
    
    PathRegistry paths;
    
    @Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
        input = container.getInput();
        Vector2f startPosition = new Vector2f(50, 300);
        player= new Player(entities, startPosition);
        Enemy.player = player;
        
        audioManager.loadAudioAssets();
        audioManager.playMusic(AudioAsset.MUSIC_LEVEL_1);
        
        paths = new PathRegistry();
        paths.loadFromJson("./data/paths.json");
        
        disObjs.add(new Background(container.getWidth(), container.getHeight()));
        hud = new HeadsUpDisplay(player);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		for (DisplayObject obj : disObjs) {
        	obj.draw(g);
        }
		
		entities.draw(g);
		
		switch(state)
		{
		case Playing:
			hud.render(g);
			break;
		case Pause:
			break;
		case MainMenu:
			mainMenu.render(g);
			break;
		case Highscore:
			highscoreList.render(g);
			break;
		case PostGame:
			renderPostGame(g);
			break;
		}		
		renderDebugText(g);
	}


	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		
		switch(state)
		{
		case Playing:
			updateGame(delta);
			break;
		case Pause:
			break;
		case MainMenu:
			menuInput(delta);
			break;
		case Highscore:
			break;
		case PostGame:
			postGameInput();
			break;
		}
		
        
        if(input.isKeyPressed(Input.KEY_ESCAPE))
        {
        	changeState(State.MainMenu);
        }
        
		/* will put this back in later
        if(Input.WasKeyPressed(KeyEvent.VK_H))
        {
            screenShot();
        }
        */
	}

	private void updateGame(int delta) {
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
        	changeState(State.PostGame);
        }
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
	
	
	private void renderPostGame(Graphics g) {		
		String gameOverText = new String("GAME OVER");
		int screenCenterX = GDCalaga.SCREEN_SIZE_X / 2;
		int screenCenterY = GDCalaga.SCREEN_SIZE_Y / 2;
		g.drawString(gameOverText, screenCenterX - (gameOverText.length() * 5), screenCenterY);
		
		String highscoreText = new String("Highscore: " + Player.getTotalPoints());
		int xPixel = screenCenterX - (highscoreText.length() * 5);
		int yPixel = screenCenterY + 15;
		g.drawString(highscoreText, xPixel, yPixel);
		
		String enterNameText = new String("Enter your name: ");
		xPixel = screenCenterX - ((enterNameText.length() + 5) * 5);
		yPixel = screenCenterY + 50;
		g.drawString(enterNameText + playerName, xPixel, yPixel);
	}
	
	
	private void postGameInput() {
		
		if(input.isKeyDown(Input.KEY_ENTER))
		{
			highscoreList.addHighscore(Player.getTotalPoints(), playerName);
			changeState(State.MainMenu);
		}
	}


	private void shipInput(int delta) {
		
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

	
	private void menuInput(int delta) {
		
		menuKeyDelayCounter -= delta;
		if(menuKeyDelayCounter > 0)
			return;
		if(input.isKeyDown(Input.KEY_W))
		{
			mainMenu.MoveSelectionUp();
		    menuKeyDelayCounter = menuKeyDelay;
		}
		if(input.isKeyDown(Input.KEY_S))
		{
			mainMenu.MoveSelectionDown();
			menuKeyDelayCounter = menuKeyDelay;
		}
		if(input.isKeyDown(Input.KEY_ENTER))
		{
			Menu.Selection selection = mainMenu.selectOption();
			switch(selection)
			{
			case NewGame:
				startNewGame();
				break;
			case ViewHighScore:
				changeState(State.Highscore);
				break;
			case ExitGame:
				CloseGame();
				break;
			}
			menuKeyDelayCounter = menuKeyDelay;
		}
		if(input.isKeyPressed(Input.KEY_ESCAPE))
        {
			if(gameInProgress)
	        	changeState(State.Playing);
        }
	}

	private void startNewGame() {
		gameInProgress = true;		
		changeState(State.Playing); //fixme reinitialize game.
	}

	private void changeState(State newState) {
		state = newState;
	}
	
	private void CloseGame() {
		highscoreList.saveHighscore();
		System.exit(0);
	}
	
	@Override
	public int getID() {
		return ID;
	}
}
