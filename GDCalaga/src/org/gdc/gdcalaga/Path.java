package org.gdc.gdcalaga;
import java.util.*;

public class Path {
	private float goalX, goalY;
	private ArrayList<PathNode> nodes;
	private int currentNode;
	
	Path(float x, float y){
		goalX = x;
		goalY = y;
		currentNode = -1;
		nodes = new ArrayList<PathNode>();
	}
	
	public void addNode(boolean relative, float x, float y, float speed){
		if(relative){
			x += goalX;
			y += goalY;
		}
		
		nodes.add(new PathNode(relative, x, y, speed));
	}
	
	public void setPos(float newX, float newY){
		for(int i = 0; i < nodes.size(); i++){
			nodes.get(i).translatePos(newX - goalX, newY - goalY);
		}
		goalX = newX;
		goalY = newY;
	}
	
	public boolean hasNext(){
		if(currentNode + 1 >= nodes.size()) return false;
		else return true;
	}
	
	public PathNode next(){
		if(hasNext()){
			currentNode++;
			return nodes.get(currentNode);
		} else {
			return null;
		}
	}
}
