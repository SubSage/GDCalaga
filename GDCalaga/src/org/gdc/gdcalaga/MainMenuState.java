package org.gdc.gdcalaga;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainMenuState extends BasicGameState {

	public enum Selection
	{
		NewGame,
		ContinueGame,
		ViewHighScore,
		ExitGame
	}
	
	private Selection currentSelection = Selection.NewGame;
	
	
	// drawing 
	private final int centerX;
	private final int centerY;
	private final int lineSpacing = 15;
	
	//controls
    private Input input;
	final int menuKeyDelay = 250; 
    int menuKeyDelayCounter = 250;
    
    //state
    boolean gameInProgress = false;
    
    //slick
    public static final int stateId = 1;
	
    
	public MainMenuState(int screenSizeX, int screenSizeY) {
		centerX = screenSizeX / 2;
		centerY = screenSizeY / 2;
	}
	
	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int deltaTime)
			throws SlickException {
		// TODO Auto-generated method stub
		
		menuInput(deltaTime, gameContainer, stateBasedGame);
	}

private void menuInput(int delta, GameContainer gameContainer, StateBasedGame game) {
		
		menuKeyDelayCounter -= delta;
		if(menuKeyDelayCounter > 0)
			return;
		if(input.isKeyDown(Input.KEY_W))
		{
			MoveSelectionUp();
		    menuKeyDelayCounter = menuKeyDelay;
		}
		if(input.isKeyDown(Input.KEY_S))
		{
			MoveSelectionDown();
			menuKeyDelayCounter = menuKeyDelay;
		}
		if(input.isKeyDown(Input.KEY_ENTER))
		{
			MainMenuState.Selection selection = selectOption();
			switch(selection)
			{
			case NewGame:
				startNewGame(gameContainer, game);
				break;
			case ContinueGame:
				ContinueGame(game);
				break;
			case ViewHighScore:
				viewHighscore(game);
				break;
			case ExitGame:
				closeGame();
				break;
			}
			menuKeyDelayCounter = menuKeyDelay;
		}
		if(input.isKeyPressed(Input.KEY_ESCAPE))
        {
			//if(gameInProgress)
	        //	changeState(State.Playing);
        }
	}
	
	public void MoveSelectionUp() {
		int selectionInt = currentSelection.ordinal();
		currentSelection = Selection.values()[Math.max(0, selectionInt - 1)];
	}
	
	public void MoveSelectionDown() {
		int selectionInt = currentSelection.ordinal();
		currentSelection = Selection.values()[Math.min(Selection.ExitGame.ordinal(), selectionInt + 1)];
	}
	
	public Selection selectOption()	{
		return currentSelection;
	}
		
	private void startNewGame(GameContainer gameContainer, StateBasedGame stateBasedGame) {
		
		PlayState playState = (PlayState)stateBasedGame.getState(PlayState.ID); 
		playState.restartState(gameContainer);
		stateBasedGame.enterState(PlayState.ID);
	}
	
	private void ContinueGame(StateBasedGame stateBasedGame)
	{
		PlayState playState = (PlayState)stateBasedGame.getState(PlayState.ID); 
		if(playState.isGameInProgress())
		{
			stateBasedGame.enterState(PlayState.ID);
		}
	}
	
	private void closeGame() {
		System.exit(0);
	}
	
	private void viewHighscore(StateBasedGame stateBasedGame) {
		stateBasedGame.enterState(HighscoreState.stateId);
	}

	@Override
	public void init(GameContainer gameContainer, StateBasedGame arg1)
			throws SlickException {
		// TODO Auto-generated method stub
        input = gameContainer.getInput();
	}


	@Override
	public void render(GameContainer gameContainer, StateBasedGame arg1, Graphics g)
			throws SlickException {
		// TODO Auto-generated method stub
		drawSelector(g);
		drawMenu(g);		
	}

private void drawSelector(Graphics g) {
		
		int selectionInt = currentSelection.ordinal();
		int selectionStringlength = (Selection.values()[selectionInt].name().length() * 5);
		int selectorLength = 20;
		
		
		
        g.drawString(">>",
        			centerX - (selectorLength + selectionStringlength),
        			centerY  + (lineSpacing * selectionInt) );
        
        g.drawString("<<",
    			centerX + selectionStringlength - 10,
    			centerY  + (lineSpacing * selectionInt) );
	}
	
	private void drawMenu(Graphics g) {
        
		for(int i = 0; i < Selection.values().length; ++i)
        {
        	g.drawString(Selection.values()[i].name(),
        			centerX - (Selection.values()[i].name().length() * 5),
        			centerY + (lineSpacing * i));
        }
	}
	

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return stateId;
	}
	
	
}
