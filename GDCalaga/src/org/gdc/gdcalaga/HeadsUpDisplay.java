package org.gdc.gdcalaga;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class HeadsUpDisplay {

	private Image lifeIcon = null;
	private Image healthIcon = null;
	
	private int livesX = 100;
	private int livesY = 15;
	private int livesIconOffset = 20;
	
	private int healthX = 180;
	private int healthY = 15;
	private int healthIconOffset = 25;
	
	private int weaponX = 450;
	private int weaponY = 15;
	
	
	private int imageScale = 1;
	private Player player;
	
	public HeadsUpDisplay(Player player) {
		this.player = player;
	}
	
	public void render(Graphics g) {
		
		loadResources();
		if(player != null) {
			drawHealthBar(g);
			drawLives(g);
			drawWeapon(g);
		}
	}
	
	private void loadResources() {
		if(lifeIcon == null){
			try {
				lifeIcon = new Image("Pics/upgrade_invalid.png");	
				}
			catch (SlickException e) {
				e.printStackTrace();
				}
			
			try {
				healthIcon = new Image("Pics/HealthBar.png");
			} 
			catch (SlickException e) {
				e.printStackTrace();
			}
		}
	}

	private void drawHealthBar(Graphics g)
	{
		for(int i = 0; i < player.getHealth(); ++i)
        {
        	healthIcon.draw(healthX + (healthIconOffset*i), healthY, imageScale, Color.white);
        }
	}
	
	private void drawLives(Graphics g) {
		for(int i = 0; i < player.getLives(); ++i)
        {
			lifeIcon.draw(livesX + (livesIconOffset*i), livesY, imageScale, Color.white);
        }
	}
	
	private void drawWeapon(Graphics g) {
		
		String weaponName = new String("Weapon: " + player.getWeapon().getName());
        g.drawString(weaponName, weaponX, weaponY);
	}
}
