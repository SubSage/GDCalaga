package org.gdc.gdcalaga;
import java.util.ArrayList;

import org.newdawn.slick.geom.Vector2f;

/*
 * This class contains hard coded positions for additional gun positions
 *  on the player's ship.
 * The positions only need to be adjusted to the ship's world transform.
 * This class should only be initialized once.
 */

public class GunPositions 
{
    private static float playerWidth;
    private static float playerHeight;
    
    private static ArrayList<Vector2f> positions;
    private static ArrayList<Vector2f> directions;
    
    public GunPositions(float width, float height)
    {
        playerWidth = width;
        playerHeight = height;
        
        positions = new ArrayList<Vector2f>();
        directions = new ArrayList<Vector2f>();
        
        build();
    }
    
    private void build()
    {
        //standard front mounted gun
        positions.add(new Vector2f(playerWidth / 2, 0));
        directions.add(new Vector2f(1, 0));
        
        //front mounted gun, offset to the left
        positions.add(new Vector2f(playerWidth / 4, playerHeight / 3));
        directions.add(new Vector2f(1, 0));
        
        //front mounted gun, offset to the right
        positions.add(new Vector2f(playerWidth / 4, -playerHeight / 3));
        directions.add(new Vector2f(1, 0));
        
        //left side mounted gun
        positions.add(new Vector2f(-playerWidth / 15, playerHeight / 2));
        directions.add(new Vector2f(0, 1));
        
        //right side mounted gun
        positions.add(new Vector2f(-playerWidth / 15, -playerHeight /2));
        directions.add(new Vector2f(0, -1));
        
        //backwards
        positions.add(new Vector2f(-playerWidth / 2, 0));
        directions.add(new Vector2f(-1, 0));
        
        //diagonal front left
        positions.add(new Vector2f(playerWidth / 2, playerHeight / 2));
        directions.add(new Vector2f(1, 1));
        
        //diagonal front right
        positions.add(new Vector2f(playerWidth / 2, -playerHeight / 2));
        directions.add(new Vector2f(1, -1));
        
    }
    
    public Vector2f getPosition(int index)
    {
        return positions.get(index);
    }
    
    public Vector2f getDirection(int index)
    {
        return directions.get(index);
    }
    
    public int getSize()
    {
        if (positions.size() != directions.size()) {
            build();
        }
        return positions.size();
    }
}
