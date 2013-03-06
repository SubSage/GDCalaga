package org.gdc.gdcalaga;
import java.util.ArrayList;


public class Spawn 
{
    // I might have approached this wrongly with ents and mng...
    // As for everything else, it's cool. I want to eventually asses how the player does on a round
    // and then give them a fitting round tailored to how they did.
    public static int numberOfEnemies=1;
    public static int wave=1;
    
    public static void spawnWave(ArrayList<Entity> ents, EntityManager mng) {
        
        int countOfEnemies=0;
        
        for(Entity e:ents)
        {
            if(e instanceof Enemy)
            {
                countOfEnemies++;
            }
        }
        if(countOfEnemies==0)
        {
            for(int a=0; a<numberOfEnemies;a++)
            {
                new Enemy(mng,(int)(Math.random()*400)+800,(int)(Math.random()*400));
            }
            
            numberOfEnemies+=2;
            wave++;
        }
        
    }
    
    
}