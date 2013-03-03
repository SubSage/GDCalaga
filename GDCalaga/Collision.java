import java.util.ArrayList;



public class Collision {
    
    public static void CheckCollisions(ArrayList<Entity> ents)
    {
        
        //Very inefficient, but should work fine for now
        
        for(int i = 0; i < ents.size(); i++)
        {
            
            Entity ent1 = ents.get(i);
            
            if(ent1.shape.type != Shape.ShapeType.Null)
            {
                
                for(int j = i + 1; j < ents.size(); j++)
                {
                    
                    Entity ent2 = ents.get(j);
                    
                    if(ent2.shape.type != Shape.ShapeType.Null)
                    {
                        
                        if(ent1.shape.Intersects(ent2.shape))
                        {
                            ent1.Collide(ent2);
                            ent2.Collide(ent1);
                            
                        }
                        
                    }
                    
                }
                
            }
            
        }
        
    }
    
}
