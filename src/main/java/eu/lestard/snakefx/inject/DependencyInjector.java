package eu.lestard.snakefx.inject;

import static eu.lestard.snakefx.config.IntegerConfig.GRID_SIZE_IN_PIXEL;
import static eu.lestard.snakefx.config.IntegerConfig.MAX_SCORE_COUNT;
import static eu.lestard.snakefx.config.IntegerConfig.ROW_AND_COLUMN_COUNT;
import static eu.lestard.snakefx.config.IntegerConfig.SNAKE_START_X;
import static eu.lestard.snakefx.config.IntegerConfig.SNAKE_START_Y;
import static eu.lestard.snakefx.config.StringConfig.FXML_FILENAME_HIGHSCORE;
import static eu.lestard.snakefx.config.StringConfig.FXML_FILENAME_MAIN;
import static eu.lestard.snakefx.config.StringConfig.FXML_FILENAME_NEW_SCORE_ENTRY;
import static eu.lestard.snakefx.config.StringConfig.HIGH_SCORE_FILEPATH;

import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import eu.lestard.snakefx.core.FoodGenerator;
import eu.lestard.snakefx.core.GameLoop;
import eu.lestard.snakefx.core.Grid;
import eu.lestard.snakefx.core.Snake;
import eu.lestard.snakefx.core.SpeedLevel;
import eu.lestard.snakefx.highscore.HighScoreEntry;
import eu.lestard.snakefx.highscore.HighScoreManager;
import eu.lestard.snakefx.highscore.HighScorePersistence;
import eu.lestard.snakefx.view.ApplicationStarter;
import eu.lestard.snakefx.view.Keyboard;
import eu.lestard.snakefx.view.controller.HighScoreController;
import eu.lestard.snakefx.view.controller.MainController;
import eu.lestard.snakefx.view.controller.NewGameController;
import eu.lestard.snakefx.view.controller.NewScoreEntryController;
import eu.lestard.snakefx.view.controller.PlayPauseController;
import eu.lestard.snakefx.view.controller.SpeedChangeController;

/**
 * The DependencyInjector is the Class that manages all instances of all classes
 * and the creation process.
 * 
 * @author manuel.mauky
 * 
 */
public class DependencyInjector {

	private static final String FXML_ID_SPEED_CHOICE_BOX = "#speedChoiceBox";
	private static final String FXML_ID_POINTS_LABEL = "#pointsLabel";
	private static final String FXML_ID_PLAY_PAUSE_BUTTON = "#playPauseButton";

	private ApplicationStarter starter;

	private final Stage primaryStage;

	public DependencyInjector(final Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	/**
	 * Creates the object graph for the application with Dependency Injection.
	 */
	public void createObjectGraph() {
		final Grid grid = new Grid(ROW_AND_COLUMN_COUNT.get(),
				GRID_SIZE_IN_PIXEL.get());

		final Snake snake = new Snake(grid, SNAKE_START_X.get(),
				SNAKE_START_Y.get());

		final ReadOnlyIntegerProperty pointsProperty = snake.pointsProperty();

		final GameLoop gameLoop = new GameLoop(snake);

		final FoodGenerator foodGenerator = new FoodGenerator(grid,
				pointsProperty);

		final PlayPauseController playPauseController = new PlayPauseController(
				gameLoop);

		final NewGameController newGameController = new NewGameController(grid,
				snake, foodGenerator, gameLoop, playPauseController);

		final ObservableList<HighScoreEntry> highScoreEntries = FXCollections
				.observableArrayList();


		final Path highScoreFilepath = Paths.get(HIGH_SCORE_FILEPATH.get());

		final HighScorePersistence highScorePersistence = new HighScorePersistence(
				highScoreFilepath);

		final HighScoreManager highScoreManager = new HighScoreManager(
				highScoreEntries, MAX_SCORE_COUNT.get(), highScorePersistence);

		final NewScoreEntryController newScoreEntryController = new NewScoreEntryController(
				highScoreManager, pointsProperty);



		FxmlFactory fxmlFactory2 = createFxmlFactory();
		final Parent newScoreEntryRoot = fxmlFactory2.getFxmlRoot(
				FXML_FILENAME_NEW_SCORE_ENTRY.get(), newScoreEntryController);





		final Stage newScoreEntryStage1 = new Stage();
		newScoreEntryStage1.setScene(new Scene(newScoreEntryRoot));
		final Stage newScoreEntryStage = newScoreEntryStage1;


		final HighScoreController highScoreController = new HighScoreController(
				newScoreEntryStage, pointsProperty, highScoreEntries,
				MAX_SCORE_COUNT.get());
		final FxmlFactory fxmlFactory1 = createFxmlFactory();


		final Parent highScoreRoot = fxmlFactory1.getFxmlRoot(
				FXML_FILENAME_HIGHSCORE.get(), highScoreController);

		final Stage highScoreStage = createHighScoreStage(highScoreRoot,
				primaryStage);




		initHighScoreController(snake, highScoreController, highScoreStage);

		initNewScoreEntryStage(newScoreEntryStage, highScoreStage);


		final MainController mainController = new MainController(grid,
				newGameController, highScoreStage);
		final FxmlFactory fxmlFactory = createFxmlFactory();

		final Parent mainRoot = fxmlFactory.getFxmlRoot(FXML_FILENAME_MAIN.get(),
				mainController);

		@SuppressWarnings("unchecked")
		final ChoiceBox<SpeedLevel> speedChoiceBox = (ChoiceBox<SpeedLevel>) mainRoot
				.lookup(FXML_ID_SPEED_CHOICE_BOX);
		final SpeedChangeController speedChangeController = new SpeedChangeController(
				gameLoop, speedChoiceBox);

		speedChangeController.init();

		final Label pointsLabel = (Label) mainRoot.lookup(FXML_ID_POINTS_LABEL);

		pointsLabel.textProperty().bind(Bindings.convert(pointsProperty));

		initPlayPauseController(playPauseController, snake, mainRoot);

		final Keyboard keyboard = new Keyboard(snake);
		final Scene mainScene = createMainScene(mainRoot, keyboard);

		mainController.newGame();



		starter = new ApplicationStarter(mainScene, primaryStage);
	}

	private void initNewScoreEntryStage(final Stage newScoreEntryStage,
			final Stage owner) {
		newScoreEntryStage.initModality(Modality.WINDOW_MODAL);
		newScoreEntryStage.initOwner(owner);
	}

	private void initHighScoreController(final Snake snake,
			final HighScoreController highScoreController,
			final Stage highScoreStage) {

		ReadOnlyBooleanProperty collisionProperty = snake.collisionProperty();

		collisionProperty.addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(final ObservableValue<? extends Boolean> arg0,
					final Boolean oldValue, final Boolean newValue) {
				if (newValue) {
					highScoreStage.show();
					highScoreController.gameFinished();
				}
			}
		});
	}

	private Stage createHighScoreStage(final Parent highScoreRoot,
			final Stage owner) {
		Stage highScoreStage = new Stage();

		highScoreStage.initModality(Modality.WINDOW_MODAL);
		highScoreStage.initOwner(owner);
		highScoreStage.setScene(new Scene(highScoreRoot));
		return highScoreStage;
	}

	private Scene createMainScene(final Parent root, final Keyboard keyboard) {
		Scene mainScene = new Scene(root);
		mainScene.setOnKeyPressed(keyboard);
		return mainScene;
	}

	private void initPlayPauseController(
			final PlayPauseController playPauseController, final Snake snake,
			final Parent root) {
		final Button playPauseButton = (Button) root
				.lookup(FXML_ID_PLAY_PAUSE_BUTTON);

		playPauseButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent arg0) {
				playPauseController.togglePlayPause();
			}
		});

		playPauseController.buttonLabelProperty().bindBidirectional(
				playPauseButton.textProperty());
		playPauseController.disabledProperty().bindBidirectional(
				playPauseButton.disableProperty());

		ReadOnlyBooleanProperty collisionProperty = snake.collisionProperty();

		collisionProperty.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(final ObservableValue<? extends Boolean> arg0,
					final Boolean oldValue, final Boolean newValue) {
				if (newValue) {
					playPauseController.onCollision();
				}
			}
		});
	}

	private FxmlFactory createFxmlFactory() {
		FXMLLoader fxmlLoader = new FXMLLoader();
		return new FxmlFactory(fxmlLoader);
	}

	public ApplicationStarter getApplicationStarter() {
		return starter;
	}

}
