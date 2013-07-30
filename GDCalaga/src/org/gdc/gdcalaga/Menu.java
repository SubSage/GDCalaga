package org.gdc.gdcalaga;
import org.newdawn.slick.Graphics;

public class Menu {

	public enum Selection
	{
		NewGame,
		ViewHighScore,
		ExitGame
	}
	
	private Selection currentSelection = Selection.NewGame;
	
	private final int centerX;
	private final int centerY;
	private final int lineSpacing = 15;
	
	//menu is a singleton.
	public Menu(int screenSizeX, int screenSizeY) {
		centerX = screenSizeX / 2;
		centerY = screenSizeY / 2;
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
	
	public void render(Graphics g) {
		
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
	
	
}
