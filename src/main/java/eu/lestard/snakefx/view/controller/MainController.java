package eu.lestard.snakefx.view.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import eu.lestard.snakefx.core.Field;
import eu.lestard.snakefx.core.Grid;
import eu.lestard.snakefx.core.NewGameFunction;
import eu.lestard.snakefx.viewmodel.ViewModel;

/**
 * Controller class for the main.fxml file.
 * 
 * @author manuel.mauky
 * 
 */
public class MainController {

	@FXML
	public void initialize() {
		System.out.println("Initialize");
	}


	// @FXML
	// private Group gridContainer;
	//
	private final Grid grid;

	private final ViewModel viewModel;


	public MainController(ViewModel viewModel, Grid grid, NewGameFunction newGameFunction) {
		this.viewModel = viewModel;
		this.grid = grid;

		System.out.println("Test");
	}
	//
	// @FXML
	// public void initialize() {
	// grid.init();
	//
	// for (Field f : grid.getFields()) {
	// gridContainer.getChildren().add(f.getRectangle());
	// }
	// }
	//
	// @FXML
	// public void newGame() {
	// System.out.println("new game");
	// newGameFunction.call();
	// }
	//
	// @FXML
	// public void showHighScore() {
	// System.out.println("highscore");
	// viewModel.highscoreWindowOpenProperty().set(true);
	// }
	//
	// @FXML
	// public void about() {
	// System.out.println("about");
	// viewModel.aboutWindowOpenProperty().set(true);
	// }
	//
	// @FXML
	// public void exit() {
	// Platform.exit();
	// }

}
