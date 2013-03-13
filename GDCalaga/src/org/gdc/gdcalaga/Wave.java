package org.gdc.gdcalaga;
import java.lang.*;
import java.util.*;
import org.newdawn.slick.Graphics;

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
        
        x = 800;
        y = 50;
        type = waveType;
        yRows = rows;
        xCols = cols;
        delay = spawnDelay;
        group = new EnemyGroup(manager, 100, x, y);
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
        
    }

    public void Collide(Entity other)
    {
        
    }
    
    private void spawnEnemy(){
        if(type == "block"){
            float newX = curX * 400 / xCols;
            float newY = curY * 620 / yRows;
            Enemy newEnemy = new Enemy2(entities, newX, newY);
            newEnemy.setGroup(group, newX, newY);
            newEnemy.setPath(path.copy(x + newX, y + newY));
            curY++;
            if(curY >= yRows){
                curY = 0;
                curX++;
            }
            enemies.add(newEnemy);
        } else if(type == "stream"){
            Enemy newEnemy = new Enemy(entities, x, y);
            newEnemy.setPath(path.copy(x, y));
            enemies.add(newEnemy);
        }
        spawnedEnemies++;
    }

}
