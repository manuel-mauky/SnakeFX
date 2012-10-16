package eu.lestard.snakefx.view.controller;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import eu.lestard.snakefx.core.Field;
import eu.lestard.snakefx.core.Grid;
import eu.lestard.snakefx.core.Snake;
import eu.lestard.snakefx.core.SpeedLevel;
import eu.lestard.snakefx.util.Callback;

/**
 * Controller class for the main.fxml file.
 * @author manuel.mauky
 *
 */
public class MainController {

	@FXML
	private Group gridContainer;

	@FXML
	private ChoiceBox<SpeedLevel> speedChoiceBox;

	@FXML
	private Button playPauseButton;

	@FXML
	private Label pointsLabel;

	private Grid grid;

	private Snake snake;

	private SpeedChangeController speedChangeController;

	private PointsController pointsController;

	private NewGameController newGameController;

	private PlayPauseController playPauseController;

	public MainController(Grid grid, Snake snake,
			SpeedChangeController speedChangeController,
			PointsController pointsManager,
			NewGameController newGameController,
			PlayPauseController playPauseController) {
		this.grid = grid;
		this.snake = snake;
		this.speedChangeController = speedChangeController;
		this.pointsController = pointsManager;
		this.newGameController = newGameController;
		this.playPauseController = playPauseController;
	}

	@FXML
	public void initialize() {
		grid.init();

		for (Field f : grid.getFields()) {
			gridContainer.getChildren().add(f.getRectangle());
		}

		snake.addCollisionEventListener(new Callback() {
			@Override
			public void call() {
				playPauseButton.setDisable(true);
			}
		});

		speedChangeController.init(speedChoiceBox);

		playPauseController.init(playPauseButton);

		pointsController.init(pointsLabel);

		snake.addPointsEventListener(new Callback(){
			@Override
			public void call() {
				pointsController.addPoint();
			}
		});

		newGame();
	}

	@FXML
	public void newGame() {
		System.out.println("New Game");

		newGameController.newGame();
	}

	@FXML
	public void playPause() {
		System.out.println("Play Pause");
		playPauseController.togglePlayPause();
	}

	@FXML
	public void showHighScore() {
		System.out.println("Show High Score");
	}

	@FXML
	public void exit() {
		System.out.println("Exit");
		System.exit(0);
	}

}
