package eu.lestard.snakefx.view.controller;

import javafx.fxml.FXML;
import javafx.scene.Group;
import eu.lestard.snakefx.core.Field;
import eu.lestard.snakefx.core.Grid;

/**
 * Controller class for the main.fxml file.
 * @author manuel.mauky
 *
 */
public class MainController {

	@FXML
	private Group gridContainer;

	private Grid grid;

	private NewGameController newGameController;

	public MainController(Grid grid,
			NewGameController newGameController) {
		this.grid = grid;
		this.newGameController = newGameController;
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
		System.out.println("New Game");
		newGameController.newGame();
	}

	@FXML
	public void showHighScore() {
		System.out.println("Show High Score");
	}

	@FXML
	public void exit() {
		System.out.println("Exit");
		System.exit(0);
	}

}
