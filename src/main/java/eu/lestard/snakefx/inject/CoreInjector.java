package eu.lestard.snakefx.inject;

import static eu.lestard.snakefx.config.IntegerConfig.GRID_SIZE_IN_PIXEL;
import static eu.lestard.snakefx.config.IntegerConfig.ROW_AND_COLUMN_COUNT;
import static eu.lestard.snakefx.config.IntegerConfig.SNAKE_START_X;
import static eu.lestard.snakefx.config.IntegerConfig.SNAKE_START_Y;
import javafx.beans.property.ReadOnlyIntegerProperty;
import eu.lestard.snakefx.core.FoodGenerator;
import eu.lestard.snakefx.core.GameLoop;
import eu.lestard.snakefx.core.Grid;
import eu.lestard.snakefx.core.Snake;

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
	private final ReadOnlyIntegerProperty pointsProperty;

	public CoreInjector() {
		grid = new Grid(ROW_AND_COLUMN_COUNT.get(), GRID_SIZE_IN_PIXEL.get());

		snake = new Snake(grid, SNAKE_START_X.get(), SNAKE_START_Y.get());

		gameLoop = new GameLoop(snake);

		pointsProperty = snake.pointsProperty();

		foodGenerator = new FoodGenerator(grid, pointsProperty);
	}

	public Grid getGrid() {
		return grid;
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

	public ReadOnlyIntegerProperty getPointsProperty() {
		return pointsProperty;
	}

}
