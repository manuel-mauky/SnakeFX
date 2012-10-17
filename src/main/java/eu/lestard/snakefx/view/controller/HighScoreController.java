package eu.lestard.snakefx.view.controller;

import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import eu.lestard.snakefx.highscore.HighScoreEntry;

public class HighScoreController {

	private int scoreCount;

	@FXML
	private TableView<HighScoreEntry> tableView;

	private IntegerProperty pointsProperty;

	private Stage newScoreEntryStage;

	private ObservableList<HighScoreEntry> highScoreEntries = FXCollections.observableArrayList();



	public HighScoreController( Stage newScoreEntryStage,
				IntegerProperty pointsProperty,
				ObservableList<HighScoreEntry> highScoreEntries,
				int scoreCount){
		this.newScoreEntryStage = newScoreEntryStage;
		this.pointsProperty = pointsProperty;
		this.highScoreEntries = highScoreEntries;
		this.scoreCount = scoreCount;
	}

	public void gameFinished(){
		int points = pointsProperty.get();

		int size = highScoreEntries.size();

		if(size < scoreCount){
			newScoreEntryStage.show();
		}else{
			// check whether the last entry on the list has more points then the current game

			if(highScoreEntries.get(size - 1).getPoints() < points){
				newScoreEntryStage.show();
			}
		}
	}

	@FXML
	public void initialize(){
		tableView.setItems(highScoreEntries);
	}



}
