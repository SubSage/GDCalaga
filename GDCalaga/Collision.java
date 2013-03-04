import java.util.ArrayList;



public class Collision {
    
    public static void CheckCollisions(ArrayList<Entity> ents)
    {
        
        //Very inefficient, but should work fine for now
        
        for(int entA = 0; entA < ents.size(); entA++)
        {
            
            if(ents.get(entA).shape.type != Shape.ShapeType.Null)
            {
                
                for(int entB = entA + 1; entB < ents.size(); entB++)
                {
                    
                    if(ents.get(entB).shape.type != Shape.ShapeType.Null)
                    {
                        
                        if(ents.get(entA).shape.Intersects(ents.get(entB).shape))
                        {
                            ents.get(entA).Collide(ents.get(entB));
                            ents.get(entB).Collide(ents.get(entA));
                            
                        }
                        
                    }
                    
                }
                
            }
            
        }
        
    }
    
}
