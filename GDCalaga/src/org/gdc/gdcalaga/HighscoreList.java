package org.gdc.gdcalaga;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.gdc.gdcalaga.HighscoreElement;


public class HighscoreList {

	private List<HighscoreElement> scores = new ArrayList<HighscoreElement>();
	private static HighscoreList singleton;
	
	
	public static HighscoreList getSingleton() {
		if(singleton == null)
			singleton = new HighscoreList();
		return singleton;
	}
	
	// singleton
	private HighscoreList() {
		
	}
	
	public List<HighscoreElement> getHighscoreList() {
		//if the scores list is empty read highscores from file.
		if(scores.size() == 0)
		{
			try {
				//String current = new java.io.File( "." ).getCanonicalPath();
		        //System.out.println("Current dir:"+current);
		        
				BufferedReader in = new BufferedReader(new FileReader("highscore.dat"));
				
				String line;
				while ((line = in.readLine()) != null) {
					  
					  String[] split = line.split(",");
					  scores.add(new HighscoreElement(Integer.parseInt(split[0]), split[1]));
					  }
				in.close();
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Collections.sort(scores,Collections.reverseOrder());
		}			
		
		return scores;
	}
	
	public void addHighscore(int score, String name) {
		
		scores.add(new HighscoreElement(score,name));
		Collections.sort(scores,Collections.reverseOrder());
	}
	
	public void saveHighscore()
	{
		try {
			//String current = new java.io.File( "." ).getCanonicalPath();
	        //System.out.println("Current dir:"+current);
	        
			BufferedWriter out = new BufferedWriter(new FileWriter("highscore.dat"));
			
			for(int i = 0; i < scores.size(); ++i)
			{
				out.write( scores.get(i).getScore() + "," + scores.get(i).getName() + "\n");
			}
			
			out.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
