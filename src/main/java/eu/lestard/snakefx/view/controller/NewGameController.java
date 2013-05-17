package eu.lestard.snakefx.view.controller;

import javafx.animation.Animation.Status;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import eu.lestard.snakefx.core.FoodGenerator;
import eu.lestard.snakefx.core.Grid;
import eu.lestard.snakefx.core.Snake;

public class NewGameController {

	private final Grid grid;

	private final Snake snake;

	private final FoodGenerator foodGenerator;

	private final PlayPauseController playPauseController;

	public ObjectProperty<Status> gameloopStatus = new SimpleObjectProperty<>();

	public NewGameController(final Grid grid, final Snake snake, final FoodGenerator foodGenerator,
			final PlayPauseController playPauseController) {
		this.grid = grid;
		this.snake = snake;
		this.foodGenerator = foodGenerator;
		this.playPauseController = playPauseController;
	}

	public ObjectProperty<Status> gameloopStatus() {
		return gameloopStatus;
	}

	public void newGame() {
		gameloopStatus.set(Status.STOPPED);

		playPauseController.enableButton();

		grid.newGame();

		snake.newGame();

		foodGenerator.generateFood();

		gameloopStatus.set(Status.RUNNING);
		gameloopStatus.set(Status.PAUSED);
	}

}
