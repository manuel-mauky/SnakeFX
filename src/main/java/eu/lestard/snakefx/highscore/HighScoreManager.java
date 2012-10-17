package eu.lestard.snakefx.highscore;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class HighScoreManager {

	private ObservableList<HighScoreEntry> highScoreEntries;

	private int scoreCount;

	public HighScoreManager(ObservableList<HighScoreEntry> highScoreEntries, int scoreCount){
		this.highScoreEntries= highScoreEntries;
		this.scoreCount = scoreCount;
	}

	public void addScore(String name, int points){
		HighScoreEntry entry = new HighScoreEntry(1,name, points);

		highScoreEntries.add(entry);

		updateList();
	}

	private void updateList(){
		FXCollections.sort(highScoreEntries);

		for(int i=0 ; i<highScoreEntries.size() ; i++){
			if(i<scoreCount){
				highScoreEntries.get(i).setRanking(i+1);
			}else{
				highScoreEntries.remove(i);
			}
		}
	}
}
