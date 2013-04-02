package org.gdc.gdcalaga;
import java.util.*;
import java.io.*;
import java.lang.reflect.*;
import com.google.gson.Gson;
import com.google.gson.reflect.*;
import org.newdawn.slick.geom.Vector2f;

public class PathRegistry {
    private ArrayList<Path> paths;
    
    public PathRegistry(){
        paths = new ArrayList<Path>();
    }
    
    public Path getPath(int id, Vector2f pos)
    {
        if (paths.size() > id)
        {
            Path retPath = paths.get(id).copy(pos);
            return retPath;
        } else {
            return null;
        }
    }
    
    public void registerPath(Path newPath){
        paths.add(newPath);
    }
    
    public void loadFromJson(String path){
        String buffer = "";
        FileReader file = null;
        
        try{
            file = new FileReader(path);
            BufferedReader read = new BufferedReader(file);
            String line = "";
            while((line = read.readLine()) != null){
                buffer += line + "\n";
            }
            read.close();
        } catch(Exception e){
            throw new RuntimeException(e);
        }
        
        try{
        file.close();
        } catch(IOException e){
        }
        
        Type collectionType = new TypeToken<Collection<PathNode[]>>(){}.getType();
        
        ArrayList<PathNode[]> nodes = new Gson().fromJson(buffer, collectionType);
        
        for(PathNode[] nodeArray: nodes)
        {    
            Vector2f pathPos = new Vector2f(0, 0);
            Path newPath = new Path(pathPos);
            
            for(PathNode node : nodeArray)
            {
                node.initialize();  //to solve issues with json
                newPath.addNode(node.relative, node.goalPos, node.speed);
            }
            registerPath(newPath);
        }
    }
}
