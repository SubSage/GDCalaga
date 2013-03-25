package org.gdc.gdcalaga;

import org.newdawn.slick.geom.Vector2f;

public class RectShape extends Shape {

    public Vector2f pos, size;
    
    /**
     * @param position Takes a Vector2f that represents the position in x and y 
     * coordinates.
     * @param size Takes a Vector2f that represents the size in x and y floats.
     */
    RectShape(Vector2f position, Vector2f size){
        type = ShapeType.Rectangle;
        
        this.pos = position;
        this.size = size;
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
        /*if(other.xpos + other.width/2 < xpos - width/2 ||
            other.xpos - other.width/2 > xpos + width/2 ||
            other.ypos + other.height/2 < ypos - height/2 ||
            other.ypos - other.height/2 > ypos + height/2){
            
            return false;
        } else {
            return true;
        }*/
        
        if (other.pos.x + other.size.x / 2 < this.pos.x - this.size.x / 2
         || other.pos.x - other.size.x / 2 > this.pos.x + this.size.x / 2
         || other.pos.y + other.size.y / 2 < this.pos.y - this.size.y / 2
         || other.pos.y - other.size.y / 2 > this.pos.y + this.size.y / 2)
        {
            return false;
        } 
        else 
        {
            return true;
        }  
    }
}
