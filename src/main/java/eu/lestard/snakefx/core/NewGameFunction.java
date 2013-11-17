package eu.lestard.snakefx.core;

import eu.lestard.snakefx.viewmodel.ViewModel;
import javafx.animation.Animation.Status;

import java.util.function.Consumer;

/**
 * The purpose of this function is to start a new Game.
 * 
 * @author manuel.mauky
 * 
 */
public class NewGameFunction implements Consumer<Void> {
	private final ViewModel viewModel;
	private final Grid grid;
	private final Snake snake;
	private final FoodGenerator foodGenerator;

	public NewGameFunction(final ViewModel viewModel, final Grid grid, final Snake snake,
			final FoodGenerator foodGenerator) {
		this.viewModel = viewModel;
		this.grid = grid;
		this.snake = snake;
		this.foodGenerator = foodGenerator;
	}

    @Override
    public void accept(Void aVoid) {
        viewModel.gameloopStatus.set(Status.STOPPED);

        grid.newGame();

        snake.newGame();

        foodGenerator.generateFood();

        viewModel.gameloopStatus.set(Status.RUNNING);
        viewModel.gameloopStatus.set(Status.PAUSED);
    }
}
