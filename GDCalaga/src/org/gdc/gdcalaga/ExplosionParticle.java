import org.newdawn.slick.*;
import org.newdawn.slick.particles.*;


public class ExplosionParticle{
	
	private Image img;
	private float xpos, ypos, width, height, xvel, yvel;
	private Color color;
	private int srcx, srcy, srcwidth, srcheight;
	private Explosion exp;

	public ExplosionParticle(Explosion explosion, float x, float y, float w, float h) {
		xpos = x;
		ypos = y;
		width = w;
		height = h;
		color = new Color(1,1,1,1);
		exp = explosion;
		
		xvel = 5;
		yvel = 5;
	}
	
	public void SetImage(Image image, int xoffset, int yoffset, int srcw, int srch){
		img = image;
		srcx = xoffset;
		srcy = yoffset;
		srcwidth = srcw;
		srcheight = srch;
	}
	
	public void SetVelocity(){
		xvel *= (xpos - exp.xpos) * (float)Math.random();
		yvel *= (ypos - exp.ypos) * (float)Math.random();
	}
	
	public void SetColor(float r, float g, float b, float a){
		color.r = r;
		color.g = g;
		color.b = b;
		color.a = a;
	}
	
	public void update(float delta){
		xpos += xvel * delta / 1000;
		ypos += yvel * delta / 1000;
	}
	
	public void render(Graphics g){
		img.draw(xpos, ypos, xpos + width, ypos + height, srcx, srcy, srcx + srcwidth, srcy + srcheight, color);
	}
	
}
