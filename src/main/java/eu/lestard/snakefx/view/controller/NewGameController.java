package eu.lestard.snakefx.view.controller;

import javafx.beans.property.IntegerProperty;
import eu.lestard.snakefx.core.FoodGenerator;
import eu.lestard.snakefx.core.GameLoop;
import eu.lestard.snakefx.core.Grid;
import eu.lestard.snakefx.core.Snake;

public class NewGameController {

	private Grid grid;

	private Snake snake;

	private FoodGenerator foodGenerator;

	private GameLoop gameLoop;

	private PlayPauseController playPauseController;

	private IntegerProperty pointsProperty;

	public NewGameController(Grid grid, Snake snake,
			FoodGenerator foodGenerator, GameLoop gameLoop,
			PlayPauseController playPauseController,
			IntegerProperty pointsProperty) {
		this.grid = grid;
		this.snake = snake;
		this.foodGenerator = foodGenerator;
		this.gameLoop = gameLoop;
		this.playPauseController = playPauseController;
		this.pointsProperty = pointsProperty;
	}

	public void newGame() {
		gameLoop.init();

		playPauseController.enableButton();

		grid.newGame();

		snake.newGame();

		foodGenerator.generateFood();

		pointsProperty.setValue(0);

		gameLoop.pause();
	}

}
