package org.gdc.gdcalaga;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Explosion {

	private int xtiles, ytiles;
	public float xpos, ypos;
	private float width, height, life, maxlife;
	private ExplosionParticle[] particles;
	private boolean dead;
	
	Explosion(float x, float y, int xnum, int ynum, float w, float h)
	{
		xpos = x;
		ypos = y;
		width = w;
		height = h;
		xtiles = xnum;
		ytiles = ynum;
		particles = new ExplosionParticle[xtiles * ytiles];
		
		maxlife = 500f;
		life = maxlife;
		dead = false;
	}
	
	public void SetImage(Image img){
		int imgHeight = img.getHeight();
		int imgWidth = img.getWidth();
		int srcw = (int)(imgWidth / xtiles);
		int srch = (int)(imgHeight / ytiles);
		for(int tile = 0; tile < xtiles * ytiles; tile++){
			int yTile = (int) Math.floor(tile / xtiles);
			int xTile = tile - yTile * xtiles;
			
			int srcYPos = yTile * srch;
			int srcXPos = xTile * srcw;
			
			float w = (width / xtiles);
			float h = (height / ytiles);
			float x = xTile * w + xpos - width/2;
			float y = yTile * h + ypos - height/2;
			particles[tile] = new ExplosionParticle(this, x, y, w, h);
			particles[tile].SetImage(img, srcXPos, srcYPos, srcw, srch);
		}
		SetVelocity();
	}
	
	public void SetVelocity(){
		for(int tile = 0; tile < xtiles * ytiles; tile++){
 			particles[tile].SetVelocity();
		}
	}
	
	public void update(float delta){
		life -= delta;
		if(life <= 0) dead = true;
		
		if(!dead){
			for(int tile = 0; tile < xtiles * ytiles; tile++){
				float alpha = (life/maxlife);
				particles[tile].SetColor(1f, 1f, 1f, alpha);
				particles[tile].update(delta);
			}
		}
	}
	
	public void render(Graphics g){
		if(!dead){
			for(int tile = 0; tile < xtiles * ytiles; tile++){
				particles[tile].render(g);
			}
		}
	}
	
	public boolean IsDead(){
		return dead;
	}
	
	
}
