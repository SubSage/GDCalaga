package org.gdc.gdcalaga;

import java.util.LinkedList;
import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

public class Background extends DisplayObject {

	private Image bgImg;
	private int wCount;
	private int hCount;
	private float maxX;
	private float maxY;
	
	private LinkedList<Point> bgPoints = new LinkedList<Point>();
	private Image planet1;
	private float p1x, p1y;
	private Image planet2;
	private float p2x, p2y;
	
	public Background(int width, int height) throws SlickException {
	
		bgImg = new Image("./Pics/space.png");
		planet1 = new Image("./Pics/planet1.png");
		planet2 = new Image("./Pics/planet2.png");
		
		wCount = width / bgImg.getWidth() + 2;
		hCount = height / bgImg.getHeight() + 2;
		
		int x = 0;
		int y = 0;
		for (int row = 0; row < hCount; row++) {
			for (int col = 0; col < wCount; col++) {
				bgPoints.add(new Point(x, y));
				x = x + bgImg.getWidth();
			}
			x = 0;
			y = y + bgImg.getHeight();
		}
		
		maxX = bgImg.getWidth() * wCount;
		maxY = bgImg.getHeight() * hCount;
		
		Random rand = new Random(System.currentTimeMillis());
		p1x = width / 2 + rand.nextInt(width/2);
		p1y = height / 2 + rand.nextInt(height/2);
		p2x = width / 2 + rand.nextInt(width/2);
		p2y = height / 2 + rand.nextInt(height/2);
	}
	
	@Override
	public void update(float delta) {
		float diffX = -5 * delta / 1000;
		float diffY = -2 * delta / 1000;
		
		maxX += diffX;
		maxY += diffY;
		boolean updateX = false;
		boolean updateY = false;
		for (Point p : bgPoints) {
			p.setX(p.getX()+diffX);
			p.setY(p.getY()+diffY);
			
			if (p.getX() < -bgImg.getWidth()) {
				p.setX(maxX);
				updateX = true;
			}
			
			if (p.getY() < -bgImg.getHeight()) {
				p.setY(maxY);
				updateY = true;
			}
		}
		if (updateX) {
			maxX = maxX + bgImg.getWidth();
		}
		if (updateY) {
			maxY = maxY + bgImg.getHeight();
		}
		
		diffX = -10 * delta / 1000;
		diffY = -4 * delta / 1000;
		
		p1x += diffX;
		p2x += diffX;
		
		p1y += diffY;
		p2y += diffY;
	}

	@Override
	public void draw(Graphics g) {
		for (Point p : bgPoints) {
			g.drawImage(bgImg, p.getX(), p.getY());
		}
		
		g.drawImage(planet1, p1x, p1y);
		g.drawImage(planet2, p2x, p2y);
	}
}
