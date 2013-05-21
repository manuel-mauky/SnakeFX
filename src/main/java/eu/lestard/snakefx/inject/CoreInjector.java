package eu.lestard.snakefx.inject;

import eu.lestard.snakefx.core.FoodGenerator;
import eu.lestard.snakefx.core.GameLoop;
import eu.lestard.snakefx.core.Grid;
import eu.lestard.snakefx.core.NewGameFunction;
import eu.lestard.snakefx.core.Snake;
import eu.lestard.snakefx.viewmodel.ViewModel;

/**
 * This class is the Dependency Injector for all Classes in the core package.
 * 
 * @author manuel.mauky
 * 
 */
public class CoreInjector {

	private final Grid grid;
	private final Snake snake;
	private final GameLoop gameLoop;
	private final FoodGenerator foodGenerator;
	private final NewGameFunction newGameFunction;

	public CoreInjector(final ViewModel viewModel) {
		grid = new Grid(viewModel);

		gameLoop = new GameLoop(viewModel);

		snake = new Snake(viewModel, grid, gameLoop);


		foodGenerator = new FoodGenerator(viewModel, grid);

		newGameFunction = new NewGameFunction(viewModel, grid, snake, foodGenerator);
	}

	public Snake getSnake() {
		return snake;
	}

	public GameLoop getGameLoop() {
		return gameLoop;
	}

	public FoodGenerator getFoodGenerator() {
		return foodGenerator;
	}

	public Grid getGrid() {
		return grid;
	}

	public NewGameFunction getNewGameFunction() {
		return newGameFunction;
	}
}
