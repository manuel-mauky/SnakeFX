package eu.lestard.snakefx.inject;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import eu.lestard.snakefx.config.Configurator;
import eu.lestard.snakefx.config.IntegerKey;
import eu.lestard.snakefx.core.FoodGenerator;
import eu.lestard.snakefx.core.GameLoop;
import eu.lestard.snakefx.core.Grid;
import eu.lestard.snakefx.core.Snake;
import eu.lestard.snakefx.view.ApplicationStarter;
import eu.lestard.snakefx.view.Keyboard;
import eu.lestard.snakefx.view.controller.MainController;
import eu.lestard.snakefx.view.controller.NewGameController;
import eu.lestard.snakefx.view.controller.PlayPauseController;
import eu.lestard.snakefx.view.controller.PointsController;
import eu.lestard.snakefx.view.controller.SpeedChangeController;

/**
 * The MainInjector is the Class that manages all instances of all classes and
 * the creation process.
 *
 * @author manuel.mauky
 *
 */
public class MainInjector {

	private static final String MAIN_FXML_FILENAME = "/fxml/main.fxml";

	private ApplicationStarter starter;

	/**
	 * Creates the object graph for the application with Dependency Injection.
	 */
	public void createObjectGraph() {
		Configurator configurator = new Configurator();

		int gridSizeInPixel = configurator
				.getValue(IntegerKey.GRID_SIZE_IN_PIXEL);
		int rowAndColumnCount = configurator
				.getValue(IntegerKey.ROW_AND_COLUMN_COUNT);

		Grid grid = new Grid(rowAndColumnCount, gridSizeInPixel);

		int snakeStartX = configurator.getValue(IntegerKey.SNAKE_START_X);
		int snakeStartY = configurator.getValue(IntegerKey.SNAKE_START_Y);
		Snake snake = new Snake(grid, snakeStartX, snakeStartY);

		GameLoop gameLoop = new GameLoop(snake);

		SpeedChangeController speedChangeController = new SpeedChangeController(
				gameLoop);

		FoodGenerator foodGenerator = new FoodGenerator(grid, snake);

		PointsController pointsManager = new PointsController();

		PlayPauseController playPauseController = new PlayPauseController(gameLoop);

		NewGameController newGameController = new NewGameController(grid, snake, foodGenerator, gameLoop, playPauseController);

		MainController mainController = new MainController(grid, snake, speedChangeController, pointsManager, newGameController, playPauseController);

		FXMLLoader fxmlLoader = new FXMLLoader();
		FxmlFactory fxmlFactory = new FxmlFactory(fxmlLoader);
		Parent root = fxmlFactory.getFxmlRoot(MAIN_FXML_FILENAME,
				mainController);

		Keyboard keyboard = new Keyboard(snake);
		Scene mainScene = new Scene(root);
		mainScene.setOnKeyPressed(keyboard);

		starter = new ApplicationStarter(mainScene);
	}

	public ApplicationStarter getApplicationStarter() {
		return starter;
	}

}
