package org.gdc.gdcalaga;
import java.util.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class Wave extends Entity {
    private EnemyGroup group;
    private String type;
    private int xCols, yRows, curX, curY, totalEnemies, spawnedEnemies;
    private float delay;
    private Path path;
    
    private float lastSpawn;
    private ArrayList<Enemy> enemies;
    
    public Wave(EntityManager manager, String waveType, int rows, int cols, int numEnemies, float spawnDelay, Path wavePath)
    {
        super(manager);
        
        pos = new Vector2f(800, 50);
        
        type = waveType;
        yRows = rows;
        xCols = cols;
        delay = spawnDelay;
        group = new EnemyGroup(manager, 100, pos.x, pos.y);
        path = wavePath;
        
        lastSpawn = 0;
        enemies = new ArrayList<Enemy>();
        
        if(type == "block"){
            totalEnemies = xCols * yRows;
        } else {
            totalEnemies = numEnemies;
        }
        
        
    }

    public void update(float delta)
    {
        lastSpawn += delta;
        
        if(lastSpawn >= delay && spawnedEnemies < totalEnemies){
            lastSpawn -= delay;
            spawnEnemy();
        }
        
        for(int i = enemies.size() - 1; i >= 0; i--){
            if(enemies.get(i).IsDying()) enemies.remove(i);
        }
        if(enemies.size() == 0 && spawnedEnemies >= totalEnemies){
            group.Destroy();
            Destroy();
        }
    }

    public void draw(Graphics g)
    {
        //A wave can not be drawn for it is unimaginable!
    }

    public void Collide(Entity other)
    {
    	//other.Destroy(); No body wins in a collision with a wave!
    }
    
    private void spawnEnemy(){
        if(type == "block"){
            float newX = curX * 400 / xCols;
            float newY = curY * 620 / yRows;
            Vector2f newPosition = new Vector2f(newX, newY);
            Enemy newEnemy = new Enemy(entities, newPosition);
            newEnemy.setGroup(group, newPosition);
            newEnemy.setPath(path.copy(new Vector2f(pos.x + newPosition.x, pos.y + newPosition.y)));
            curY++;
            if(curY >= yRows){
                curY = 0;
                curX++;
            }
            enemies.add(newEnemy);
        } else if(type == "stream"){
            Enemy newEnemy = new Enemy(entities, pos.copy());
            newEnemy.setPath(path.copy(pos.copy()));
            enemies.add(newEnemy);
        }
        spawnedEnemies++;
    }

}
