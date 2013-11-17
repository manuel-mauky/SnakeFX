package eu.lestard.snakefx.view.presenter;

import eu.lestard.snakefx.core.Grid;
import eu.lestard.snakefx.viewmodel.ViewModel;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import java.util.function.Consumer;

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

	private final Consumer<?> newGameFunction;


	public MainPresenter(final ViewModel viewModel, final Grid grid, final Consumer<?> newGameFunction) {
		this.viewModel = viewModel;
		this.grid = grid;
		this.newGameFunction = newGameFunction;
	}


	@FXML
	public void initialize() {
		grid.init();

        grid.getFields().forEach(field -> {
            gridContainer.getChildren().add(field.getRectangle());
        });

		newGameFunction.accept(null);
	}

}
