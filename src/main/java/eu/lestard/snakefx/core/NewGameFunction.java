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

	public NewGameFunction(final ViewModel viewModel, final Grid grid, final Snake snake, final FoodGenerator foodGenerator) {
		this.viewModel = viewModel;
		this.grid = grid;
		this.snake = snake;
		this.foodGenerator = foodGenerator;
	}

	@Override
	public void call() {
		viewModel.gameloopStatus.set(Status.STOPPED);

		grid.newGame();

		snake.newGame();

		foodGenerator.generateFood();


		viewModel.gameloopStatus.set(Status.RUNNING);
		viewModel.gameloopStatus.set(Status.PAUSED);
	}
}
