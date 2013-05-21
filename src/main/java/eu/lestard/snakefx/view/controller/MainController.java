package eu.lestard.snakefx.view.controller;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
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
	private Pane gridContainer;

	private final Grid grid;

	private final ViewModel viewModel;

	private final NewGameFunction newGameFunction;


	public MainController(final ViewModel viewModel, final Grid grid, final NewGameFunction newGameFunction) {
		this.viewModel = viewModel;
		this.grid = grid;
		this.newGameFunction = newGameFunction;
	}


	@FXML
	public void initialize() {
		System.out.println("initializing the grid");
		grid.init();

		System.out.println("gridContainer: " + gridContainer);

		for (final Field f : grid.getFields()) {
			gridContainer.getChildren().add(f.getRectangle());
		}

		newGameFunction.call();
	}

}
