package eu.lestard.snakefx.view.controller;

import javafx.beans.property.IntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import eu.lestard.snakefx.highscore.HighScoreManager;

public class NewScoreEntryController {

	@FXML
	private TextField playername;

	private HighScoreManager highScoreManager;

	private IntegerProperty pointsProperty;

	public NewScoreEntryController(HighScoreManager highScoreManager, IntegerProperty pointsProperty){
		this.highScoreManager = highScoreManager;
		this.pointsProperty = pointsProperty;
	}

	@FXML
	public void addEntry(){
		String name = playername.getText();
		highScoreManager.addScore(name, pointsProperty.get());
		playername.getScene().getWindow().hide();
	}

}
