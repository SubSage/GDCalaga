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

import org.newdawn.slick.Graphics;

public class HighscoreList {	
	private int centerX;
	private int centerY;
	private final int lineSpacing = 15;
	private final int maxScoreLength = 9;
	
	
	List<Highscore> scores = new ArrayList<Highscore>();

	
	public HighscoreList(int screenSizeX, int screenSizeY)
	{	
		this.centerX = screenSizeX / 2;
		this.centerY = screenSizeY / 2;
	}
	
	
	public void render(Graphics g) {
		drawHighscoreList(g);		
	}

	
	private void drawHighscoreList(Graphics g) {
		for(int i = 0; i < getHighscoreList().size(); ++i)
		{
			String number = String.valueOf(getHighscoreList().get(i).getScore());
			number = String.format("%1$" + maxScoreLength + "s", number); 
			String score = (i+1) + ": " +  number + " - " + getHighscoreList().get(i).getName();
			
        	g.drawString(score,
        			centerX - (maxScoreLength * 10),
        			centerY + (lineSpacing * i));
		}
	}

	
	private List<Highscore> getHighscoreList() {
		//if the scores list is empty read highscores from file.
		if(scores.size() == 0)
		{
			try {
				String current = new java.io.File( "." ).getCanonicalPath();
		        System.out.println("Current dir:"+current);
		        
				BufferedReader in = new BufferedReader(new FileReader("highscore.dat"));
				
				String line;
				while ((line = in.readLine()) != null) {
					  
					  String[] split = line.split(",");
					  scores.add(new Highscore(Integer.parseInt(split[0]), split[1]));
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
		
		scores.add(new Highscore(score,name));
		Collections.sort(scores,Collections.reverseOrder());
	}
	
	public void saveHighscore()
	{
		try {
			String current = new java.io.File( "." ).getCanonicalPath();
	        System.out.println("Current dir:"+current);
	        
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
	
	
	private class Highscore implements Comparable<Highscore> {
		
		private int score;
		private String name;
		
		public Highscore(int score, String name) {
			this.score = score;
			this.name = name;
		}
		
		public int getScore() { return score; }
		public String getName() { return name; }

		@Override
		public int compareTo(Highscore arg0) {
			Highscore otherHighScore = (Highscore)arg0;
			if(score > otherHighScore.getScore())
				return 1;
			else if (score < otherHighScore.getScore())
				return -1;
			
			return 0;
		}
	}
	
}
