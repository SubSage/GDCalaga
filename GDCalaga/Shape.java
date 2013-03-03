

public class Shape {

	public static enum ShapeType{
		Null,
		Rectangle
	}
	
	public ShapeType type;
	
	Shape(){
		type = ShapeType.Null;
	}
	
	public boolean Intersects(Shape other){
		return false;
	}
}
