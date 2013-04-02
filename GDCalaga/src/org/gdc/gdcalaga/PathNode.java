package org.gdc.gdcalaga;

import org.newdawn.slick.geom.Vector2f;

public class PathNode {
    public float speed;
    public Vector2f goalPos;
    public float goalX; //goalX and goalY to capture values from data/paths.json
    public float goalY;
    public boolean relative;
	
    PathNode(boolean rel, float x, float y, float moveSpeed){
        relative = rel;
        goalPos = new Vector2f(x, y);
        speed = moveSpeed;
    }
    
    PathNode(boolean rel, Vector2f position, float moveSpeed){
        relative = rel;
        goalPos = new Vector2f(position);
        speed = moveSpeed;
    }
	
    public void setPos(Vector2f newPosition){
        goalPos = newPosition;
    }
	
    public void translatePos(Vector2f translateVector){
        goalPos.add(translateVector);
    }
    
    /**
     * Cleanup method to convert goalX and goalY values into goalPos,
     * since our method of creating paths in json relies on initializing
     * the goalX and goalY values
     */
    public void initialize()
    {
        goalPos = new Vector2f(goalX, goalY);
    }
}
