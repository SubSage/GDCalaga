

public class Shape {

    public static enum ShapeType{
        Null,
        Rectangle
    }
    
    public ShapeType type;
    
    Shape(){
        type = ShapeType.Null;
    }
    
    public boolean Intersects(Shape other)
    {
        System.out.println("Change shape to specific shape!");
        return false;
    }
}
