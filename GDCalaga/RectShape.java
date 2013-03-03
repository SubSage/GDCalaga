
public class RectShape extends Shape {

    public float xpos, ypos, width, height;
    
    RectShape(float x, float y, float w, float h){
        type = ShapeType.Rectangle;
        xpos=x;ypos=y;width=w;height=h;
    }
    
    public boolean Intersects(Shape other){
        //Switching shape types allows for using different algorithms for different shapes
        switch(other.type){
        case Rectangle:
            return Intersects((RectShape)other);
        default:
            return false;
        }
    }
    
    private boolean Intersects(RectShape other){
        if(other.xpos + other.width/2 < xpos - width/2 ||
            other.xpos - other.width/2 > xpos + width/2 ||
            other.ypos + other.height/2 < ypos - height/2 ||
            other.ypos - other.height/2 > ypos + height/2){
            
            return false;
        } else {
            return true;
        }
    }
    
    
}
