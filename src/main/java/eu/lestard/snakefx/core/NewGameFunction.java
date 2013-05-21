package eu.lestard.snakefx.core;

import javafx.animation.Animation.Status;
import eu.lestard.snakefx.util.Function;
import eu.lestard.snakefx.viewmodel.ViewModel;

/**
 * The purpose of this function is to start a new Game.
 * 
 * @author manuel.mauky
 * 
 */
public class NewGameFunction implements Function {
	private final ViewModel viewModel;
	private final Grid grid;
	private final Snake snake;
	private final FoodGenerator foodGenerator;

	public NewGameFunction(ViewModel viewModel, Grid grid, Snake snake, FoodGenerator foodGenerator) {
		this.viewModel = viewModel;
		this.grid = grid;
		this.snake = snake;
		this.foodGenerator = foodGenerator;
	}

	@Override
	public void call() {
		viewModel.gameloopStatusProperty().set(Status.STOPPED);

		grid.newGame();

		snake.newGame();

		foodGenerator.generateFood();


		viewModel.gameloopStatusProperty().set(Status.RUNNING);
		viewModel.gameloopStatusProperty().set(Status.PAUSED);
	}
}
