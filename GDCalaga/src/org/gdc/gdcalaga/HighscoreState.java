package org.gdc.gdcalaga;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class HighscoreState extends BasicGameState {	
	private int centerX;
	private int centerY;
	private final int lineSpacing = 15;
	private final int maxScoreLength = 9;
	private final int maxScoresInFile = 10;
	
	private final String backString = ">>Back<<";
	private Input input;
	
	//slick
	public static final int stateId = 3;
	
	public HighscoreState(int screenSizeX, int screenSizeY)
	{	
		this.centerX = screenSizeX / 2;
		this.centerY = screenSizeY / 2;
	}
	
	
	private void drawHighscoreList(Graphics graphics) {
		for(int i = 0; i < HighscoreList.getSingleton().getHighscoreList().size(); ++i)
		{
			String number = String.valueOf(HighscoreList.getSingleton().getHighscoreList().get(i).getScore());
			number = String.format("%1$" + maxScoreLength + "s", number); 
			String score = (i+1) + ": " +  number + " - " + HighscoreList.getSingleton().getHighscoreList().get(i).getName();
			
        	graphics.drawString(score,
        			centerX - (maxScoreLength * 10),
        			centerY + (lineSpacing * i));
		}
	}

	private void drawBackButton(Graphics graphics) {
		graphics.drawString(backString,
    			centerX - (backString.length() * 5),
    			centerY + lineSpacing * (maxScoresInFile+1) );
		
	}
	
	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame)
			throws SlickException {
		// TODO Auto-generated method stub
		input = gameContainer.getInput();
	}


	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics)
			throws SlickException {
		// TODO Auto-generated method stub
		drawHighscoreList(graphics);
		drawBackButton(graphics);
	}


	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int deltaTime)
			throws SlickException {
		// TODO Auto-generated method stub
		if(input.isKeyDown(Input.KEY_ESCAPE)) {
			stateBasedGame.enterState(MainMenuState.stateId);
		}
		// as seems to be the case the next if statement will automatically be true.
		//else if(input.isKeyDown(Input.KEY_ENTER)) {
		//	stateBasedGame.enterState(MainMenuState.stateId);
		//}
	}


	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return stateId;
	}
	
}
