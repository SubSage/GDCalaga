package org.gdc.gdcalaga;
import java.util.*;
import java.lang.*;
import java.io.*;
import java.lang.reflect.*;
import com.google.gson.Gson;
import com.google.gson.reflect.*;

public class PathRegistry {
    private ArrayList<Path> paths;
    
    public PathRegistry(){
        paths = new ArrayList<Path>();
    }
    
    public Path getPath(int id, float x, float y){
        if(paths.size() > id){
            Path retPath = paths.get(id).copy(x, y);
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
        } catch(Exception e){
            throw new RuntimeException(e);
        }
        
        try{
        file.close();
        } catch(IOException e){
        }
        
        Type collectionType = new TypeToken<Collection<PathNode[]>>(){}.getType();
        
        ArrayList<PathNode[]> nodes = new Gson().fromJson(buffer, collectionType);
        
        for(PathNode[] nodeArray: nodes){
            Path newPath = new Path(0, 0);
            for(PathNode node : nodeArray){
                newPath.addNode(node.relative, node.goalX, node.goalY, node.speed);
            }
            registerPath(newPath);
        }
    }
}
