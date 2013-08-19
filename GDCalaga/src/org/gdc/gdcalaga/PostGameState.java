package org.gdc.gdcalaga;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class PostGameState extends BasicGameState {

	public static final int sceneId = 2;
	
	//state
	private String playerName = "";
	private String currentChar = "";
	
	private int charNumber = 97;
	private int minLetterValue = 97;
	private int maxLetterValue = 122;
	
	private int menuKeyDelayCounter = 250;

	int screenCenterX;
	int screenCenterY;
	
	private Input input;
	
	public PostGameState(int screenSizeX, int screenSizeY) {
		screenCenterX = GDCalaga.SCREEN_SIZE_X / 2;
		screenCenterY = GDCalaga.SCREEN_SIZE_Y / 2;
	}
	
	
	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame)
			throws SlickException {
		input = gameContainer.getInput();	
	}

	@Override
	public void render(GameContainer gameContiner, StateBasedGame stateBasedGame, Graphics graphics)
			throws SlickException {
		renderPostGame(graphics);
	}

	private void renderPostGame(Graphics graphics) {		
		String gameOverText = new String("GAME OVER");

		graphics.drawString(gameOverText, screenCenterX - (gameOverText.length() * 5), screenCenterY);
		
		String highscoreText = new String("Highscore: " + Player.getTotalPoints());
		int xPixel = screenCenterX - (highscoreText.length() * 5);
		int yPixel = screenCenterY + 15;
		graphics.drawString(highscoreText, xPixel, yPixel);
		
		
		//rounding errors in the calculations for character positions.
		String enterNameText = new String("Enter your name: ");
		xPixel = screenCenterX - ((enterNameText.length() + 5) * 5);
		yPixel = screenCenterY + 50;
		graphics.drawString(enterNameText + playerName, xPixel, yPixel);
		
		xPixel += (enterNameText.length() + playerName.length() - 2) * 10;
		yPixel = screenCenterY + 50;
		graphics.drawString(currentChar, xPixel, yPixel);
	}
	
	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int deltaTime)
			throws SlickException {
		
		menuKeyDelayCounter -= deltaTime;
		if(menuKeyDelayCounter > 0)
			return;
		
		if(input.isKeyPressed(Input.KEY_UP))
		{
			charNumber++;
			if (charNumber > maxLetterValue)
			{
				charNumber = minLetterValue;
			}
			currentChar = Character.toString((char)charNumber);
		}
		else if(input.isKeyPressed(Input.KEY_DOWN))
		{
			charNumber--;
			if (charNumber < minLetterValue)
			{
				charNumber = maxLetterValue;
			}
			currentChar = Character.toString((char)charNumber);
		}
		
		if(input.isKeyPressed(Input.KEY_SPACE))
		{
			playerName += currentChar;
		}
		
		// this if clause is automatically true when entering this state, for some reason.
		if(input.isKeyPressed(Input.KEY_RETURN))
		{
			stateBasedGame.enterState(MainMenuState.stateId);
			HighscoreList.getSingleton().addHighscore(Player.getTotalPoints(), playerName);
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return sceneId;
	}

}



