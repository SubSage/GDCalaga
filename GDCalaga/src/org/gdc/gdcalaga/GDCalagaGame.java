package org.gdc.gdcalaga;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class GDCalagaGame extends StateBasedGame{

	public GDCalagaGame() {
		super("GDCalaga");
	}

	public void initStatesList(GameContainer arg0) throws SlickException {
		addState(new PlayState());
	}
}
