package eu.lestard.snakefx.view.controller;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import eu.lestard.snakefx.core.Direction;
import eu.lestard.snakefx.viewmodel.ViewModel;

/**
 * This class handles the input of the user.
 * 
 * @author manuel.mauky
 */
public class KeyboardController implements EventHandler<KeyEvent> {

	private final ViewModel viewModel;

	public KeyboardController(final ViewModel viewModel) {
		this.viewModel = viewModel;
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public void handle(final KeyEvent event) {
		final KeyCode code = event.getCode();

		switch (code) {
		case UP:
			viewModel.snakeDirection.set(Direction.UP);
			break;
		case DOWN:
			viewModel.snakeDirection.set(Direction.DOWN);
			break;
		case LEFT:
			viewModel.snakeDirection.set(Direction.LEFT);
			break;
		case RIGHT:
			viewModel.snakeDirection.set(Direction.RIGHT);
			break;
		}
	}
}