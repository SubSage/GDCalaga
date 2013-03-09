package org.gdc.gdcalaga;

import java.util.LinkedList;

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
	private LinkedList<Point> floaterPoints = new LinkedList<Point>();
	private LinkedList<Integer> floaterCounts=new LinkedList<Integer>();
	private LinkedList<Image> images = new LinkedList<Image>();
	private Image planet1;
	private Image planet2;
	
	public Background(int width, int height) throws SlickException {
	
		bgImg = new Image("./Pics/space.png");
		planet1 = new Image("./Pics/planet1.png");
		planet2 = new Image("./Pics/planet2.png");
		images.add(planet1);
		images.add(planet2);
		
		wCount = width / bgImg.getWidth() + 2;
		hCount = height / bgImg.getHeight() + 2;
		
		int x = 0;
		int y = 0;
		for (int row = 0; row < hCount; row++) {
			for (int col = 0; col < wCount; col++) {
				bgPoints.add(new Point(x, y));
				x = x + bgImg.getWidth();
				
				if((int)(Math.random()*100)<4)
				{
					floaterPoints.add(new Point((int)(Math.random()*1280), (int)(Math.random()*720) ));
					floaterCounts.add((int)(Math.random()*images.size()));
				}
				
			}
			x = 0;
			y = y + bgImg.getHeight();
			
			
		}
		
		maxX = bgImg.getWidth() * wCount;
		maxY = bgImg.getHeight() * hCount;
		
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
		
		for(int f=0; f<floaterCounts.size(); f++)
		{
			Point p=floaterPoints.get(f);
			Image img=images.get(floaterCounts.get(f));
			p.setX(p.getX()+diffX);
			p.setY(p.getY()+diffY);
			if(p.getX() + img.getWidth()/2 < 0 || p.getY() + img.getHeight()/2 < 0)
			{
				floaterCounts.remove(f);
				floaterPoints.remove(f);
				f--;
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
		
		
	}

	@Override
	public void draw(Graphics g) {
		
		for (Point p : bgPoints) {
			g.drawImage(bgImg, p.getX(), p.getY());
		}
		
		for(int f=0; f<floaterCounts.size(); f++) 
		{
			Point fpts = floaterPoints.get(f);
			Image img = images.get(floaterCounts.get(f));
			g.drawImage(img , fpts.getX() - img.getWidth()/2 ,fpts.getY() + img.getHeight()/2);
			
		
		}
		
	}
	
	public void addRandomFloater()
	{
		floaterPoints.add(new Point((int)(Math.random()*100)+1300, (int)(Math.random()*720) ));
		floaterCounts.add((int)(Math.random()*images.size()));
		
	}
}
