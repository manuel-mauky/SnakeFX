package eu.lestard.snakefx.view.controller;

import eu.lestard.snakefx.core.FoodGenerator;
import eu.lestard.snakefx.core.GameLoop;
import eu.lestard.snakefx.core.Grid;
import eu.lestard.snakefx.core.Snake;

public class NewGameController {

	private final Grid grid;

	private final Snake snake;

	private final FoodGenerator foodGenerator;

	private final GameLoop gameLoop;

	private final PlayPauseController playPauseController;

	public NewGameController(final Grid grid, final Snake snake,
			final FoodGenerator foodGenerator, final GameLoop gameLoop,
			final PlayPauseController playPauseController) {
		this.grid = grid;
		this.snake = snake;
		this.foodGenerator = foodGenerator;
		this.gameLoop = gameLoop;
		this.playPauseController = playPauseController;
	}

	public void newGame() {
		gameLoop.init();

		playPauseController.enableButton();

		grid.newGame();

		snake.newGame();

		foodGenerator.generateFood();

		gameLoop.pause();
	}

}
