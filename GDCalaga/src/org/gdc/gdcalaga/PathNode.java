package org.gdc.gdcalaga;

public class PathNode {
	public float goalX, goalY, speed;
	public boolean relative;
	
	PathNode(boolean rel, float x, float y, float moveSpeed){
		relative = rel;
		goalX = x;
		goalY = y;
		speed = moveSpeed;
	}
	
	public void setPos(float newX, float newY){
		goalX = newX;
		goalY = newY;
	}
	
	public void translatePos(float addX, float addY){
		goalX += addX;
		goalY += addY;
	}
}
