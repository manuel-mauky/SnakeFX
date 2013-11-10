package eu.lestard.snakefx.view.presenter;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import eu.lestard.snakefx.core.Field;
import eu.lestard.snakefx.core.Grid;
import eu.lestard.snakefx.core.NewGameFunction;
import eu.lestard.snakefx.viewmodel.ViewModel;

/**
 * Presenter class for the main.fxml file.
 * 
 * @author manuel.mauky
 * 
 */
public class MainPresenter {

	@FXML
	private Pane gridContainer;

	private final Grid grid;

	private final ViewModel viewModel;

	private final NewGameFunction newGameFunction;


	public MainPresenter(final ViewModel viewModel, final Grid grid, final NewGameFunction newGameFunction) {
		this.viewModel = viewModel;
		this.grid = grid;
		this.newGameFunction = newGameFunction;
	}


	@FXML
	public void initialize() {
		grid.init();

		for (final Field f : grid.getFields()) {
			gridContainer.getChildren().add(f.getRectangle());
		}

		newGameFunction.call();
	}

}
