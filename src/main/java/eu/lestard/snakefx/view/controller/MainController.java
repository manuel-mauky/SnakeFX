package eu.lestard.snakefx.view.controller;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.stage.Stage;
import eu.lestard.snakefx.core.Field;
import eu.lestard.snakefx.core.Grid;

/**
 * Controller class for the main.fxml file.
 * 
 * @author manuel.mauky
 * 
 */
public class MainController {

	@FXML
	private Group gridContainer;

	private final Grid grid;

	private final NewGameController newGameController;

	private final Stage highScoreStage;

	private final Stage aboutStage;

	public MainController(Grid grid, NewGameController newGameController, Stage highScoreStage, Stage aboutStage) {
		this.grid = grid;
		this.newGameController = newGameController;
		this.highScoreStage = highScoreStage;
		this.aboutStage = aboutStage;

	}


	@FXML
	public void initialize() {
		grid.init();

		for (Field f : grid.getFields()) {
			gridContainer.getChildren().add(f.getRectangle());
		}
	}

	@FXML
	public void newGame() {
		newGameController.newGame();
	}

	@FXML
	public void showHighScore() {
		highScoreStage.show();
	}

	@FXML
	public void about() {
		aboutStage.show();
	}

	@FXML
	public void exit() {
		System.exit(0);
	}

}
