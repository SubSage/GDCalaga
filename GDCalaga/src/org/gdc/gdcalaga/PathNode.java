package org.gdc.gdcalaga;

import org.newdawn.slick.geom.Vector2f;

public class PathNode {
    public float speed;
    public Vector2f goalPos;
    public boolean relative;
	
    PathNode(boolean rel, Vector2f position, float moveSpeed){
        relative = rel;
        goalPos = position;
        speed = moveSpeed;
    }
	
    public void setPos(Vector2f newPosition){
        goalPos = newPosition;
    }
	
    public void translatePos(Vector2f translateVector){
        goalPos.add(translateVector);
    }
}
