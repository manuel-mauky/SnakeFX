package eu.lestard.snakefx.inject;

import static eu.lestard.snakefx.config.StringConfig.FXML_FILENAME_ABOUT;
import static eu.lestard.snakefx.config.StringConfig.FXML_FILENAME_HIGHSCORE;
import static eu.lestard.snakefx.config.StringConfig.FXML_FILENAME_MAIN;
import static eu.lestard.snakefx.config.StringConfig.FXML_FILENAME_NEW_SCORE_ENTRY;
import static eu.lestard.snakefx.config.StringConfig.HIGH_SCORE_FILEPATH;

import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import eu.lestard.snakefx.highscore.HighScoreEntry;
import eu.lestard.snakefx.highscore.HighScoreManager;
import eu.lestard.snakefx.highscore.HighScorePersistence;
import eu.lestard.snakefx.view.controller.AboutController;
import eu.lestard.snakefx.view.controller.HighScoreController;
import eu.lestard.snakefx.view.controller.MainController;
import eu.lestard.snakefx.view.controller.NewGameController;
import eu.lestard.snakefx.view.controller.NewScoreEntryController;
import eu.lestard.snakefx.view.controller.PlayPauseController;
import eu.lestard.snakefx.view.controller.SpeedChangeController;
import eu.lestard.snakefx.viewmodel.ViewModel;


/**
 * This class is the Dependency Injector for all Controller classes for the
 * view.
 * 
 * @author manuel.mauky
 * 
 */
public class ControllerInjector {

	private final PlayPauseController playPauseController;
	private final NewGameController newGameController;
	private final HighScorePersistence highScorePersistence;
	private final HighScoreManager highScoreManager;
	private final NewScoreEntryController newScoreEntryController;
	private final HighScoreController highScoreController;
	private final Stage newScoreEntryStage;
	private final MainController mainController;
	private final SpeedChangeController speedChangeController;
	private final AboutController aboutController;
	private final Parent mainRoot;

	public ControllerInjector(final Stage primaryStage, final CoreInjector coreInjector,
			final FxmlFactory fxmlFactory, final StageFactory stageFactory,
			final ControllerInitializer initializer, ViewModel viewModel) {
		Path highScoreFilepath = Paths.get(HIGH_SCORE_FILEPATH.get());

		playPauseController = new PlayPauseController(viewModel);

		newGameController = new NewGameController(coreInjector.getGrid(), coreInjector.getSnake(),
				coreInjector.getFoodGenerator(), playPauseController);


		highScorePersistence = new HighScorePersistence(highScoreFilepath);

		highScoreManager = new HighScoreManager(highScorePersistence);

		newScoreEntryController = new NewScoreEntryController(highScoreManager);
		newScoreEntryController.pointsProperty().bind(viewModel.pointsProperty());

		newScoreEntryStage = stageFactory.createStage(FXML_FILENAME_NEW_SCORE_ENTRY, newScoreEntryController);

		highScoreController = new HighScoreController(newScoreEntryStage);

		highScoreController.highScoreEntries().bind(highScoreManager.highScoreEntries());


		Stage highScoreStage = stageFactory.createStage(FXML_FILENAME_HIGHSCORE, highScoreController);

		setToModal(highScoreStage, primaryStage);

		setToModal(newScoreEntryStage, highScoreStage);

		initializer.initHighScoreController(coreInjector.getSnake(), highScoreController, highScoreStage,
				viewModel);

		aboutController = new AboutController();

		Stage aboutStage = stageFactory.createStage(FXML_FILENAME_ABOUT, aboutController);



		mainController = new MainController(coreInjector.getGrid(), newGameController, highScoreStage, aboutStage);

		mainRoot = fxmlFactory.getFxmlRoot(FXML_FILENAME_MAIN.get(), mainController);


		speedChangeController = initializer.createSpeedChangeController(mainRoot);
		speedChangeController.init();

	}

	private void setToModal(final Stage current, final Stage owner) {
		current.initModality(Modality.WINDOW_MODAL);
		current.initOwner(owner);
	}

	public PlayPauseController getPlayPauseController() {
		return playPauseController;
	}


	public MainController getMainController() {
		return mainController;
	}

	public Parent getMainRoot() {
		return mainRoot;
	}



}
