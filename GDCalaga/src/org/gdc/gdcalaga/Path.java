package org.gdc.gdcalaga;
import java.util.*;
import org.newdawn.slick.geom.Vector2f;

public class Path extends Object {
	private Vector2f goalPos;
	private ArrayList<PathNode> nodes;
	private int currentNode;
	
	public Path(Vector2f pos)
	{
	    goalPos = new Vector2f(pos);
	    currentNode = -1;
	    nodes = new ArrayList<PathNode>();
	}
	
	public Path copy(Vector2f pos)
	{  
	    Path newPath = new Path(pos);
	    for (PathNode node : nodes)
	    {
	        newPath.addNode(node.relative, node.goalPos, node.speed);
	    }
	    return newPath;
	}

	public void addNode(boolean relative, Vector2f otherPos, float speed)
	{
	    if (relative)
	    {
	        otherPos.add(this.goalPos);
	    }
	    nodes.add(new PathNode(relative, otherPos, speed));
	}
	
	public void setPos(Vector2f newPos)
	{
	    for (int i = 0; i < nodes.size(); i++)
	    {
	        //I wish operator overloading was in java
	        nodes.get(i).translatePos(newPos.copy().sub(goalPos));
	    }
	    goalPos.set(newPos);
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
