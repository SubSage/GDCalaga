import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

public class GDCalagaGame extends StateBasedGame{

	public GDCalagaGame() {
		super("GDCalaga");
		
	}

	public void initStatesList(GameContainer arg0) throws SlickException {
		addState(new PlayState());
	}
	
    public static void main(String[] argv) {
        try {
                AppGameContainer container = new AppGameContainer(new GDCalagaGame());
                container.setDisplayMode(1280,720,false);
                container.start();
        } catch (SlickException e) {
                e.printStackTrace();
        }
    }
}
