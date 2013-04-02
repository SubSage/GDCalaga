package org.gdc.gdcalaga;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;


public class ExplosionParticle{
	
	private Image img;
	private Vector2f pos, size, velocity;
	private Color color;
	private int srcx, srcy, srcwidth, srcheight;
	private Explosion exp;

	public ExplosionParticle(Explosion explosion, float x, float y, float w, float h) {
	    pos = new Vector2f(x, y);
	    size = new Vector2f(w, h);
	    velocity = new Vector2f(5, 5);
		color = new Color(1,1,1,1);
		exp = explosion;
	}
	
	public void SetImage(Image image, int xoffset, int yoffset, int srcw, int srch){
		img = image;
		srcx = xoffset;
		srcy = yoffset;
		srcwidth = srcw;
		srcheight = srch;
	}
	
	public void SetVelocity(){
		velocity.x *= (pos.x - exp.pos.x) * (float)Math.random();
		velocity.y *= (pos.y - exp.pos.y) * (float)Math.random(); 
	}
	
	public void SetColor(float r, float g, float b, float a){
		color.r = r;
		color.g = g;
		color.b = b;
		color.a = a;
	}
	
	public void update(float delta)
	{
		pos.x += velocity.x * delta / 1000;
		pos.y += velocity.y * delta / 1000;
	}
	
	public void render(Graphics g){
		img.draw(pos.x, pos.y, pos.x + size.x, pos.y + size.y, 
		        srcx, srcy, srcx + srcwidth, srcy + srcheight, color);
	}
	
}
