package org.gdc.gdcalaga;

public class HighscoreElement implements Comparable<HighscoreElement> {
	
	private int score;
	private String name;
	
	public HighscoreElement(int score, String name) {
		this.score = score;
		this.name = name;
	}
	
	public int getScore() { return score; }
	public String getName() { return name; }

	@Override
	public int compareTo(HighscoreElement arg0) {
		HighscoreElement otherHighScore = (HighscoreElement)arg0;
		if(score > otherHighScore.getScore())
			return 1;
		else if (score < otherHighScore.getScore())
			return -1;
		
		return 0;
	}
}