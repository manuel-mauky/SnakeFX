package eu.lestard.snakefx.view;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.ChoiceBox;
import eu.lestard.snakefx.core.Field;
import eu.lestard.snakefx.core.GameLoop;
import eu.lestard.snakefx.core.Grid;
import eu.lestard.snakefx.core.Snake;
import eu.lestard.snakefx.core.SpeedLevel;

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

	private Snake snake;

	private Grid grid;

	private GameLoop gameLoop;

	private SpeedChangeController speedChangeController;

	public MainController(Grid grid, Snake snake, GameLoop gameLoop, SpeedChangeController speedChangeController){
		this.grid = grid;
		this.snake = snake;
		this.gameLoop = gameLoop;
		this.speedChangeController = speedChangeController;
	}

	@FXML
	public void initialize(){
		grid.init();

		snake.init();

		gameLoop.init();

		speedChangeController.init(speedChoiceBox);

		for(Field f : grid.getFields()){
			gridContainer.getChildren().add(f.getRectangle());
		}
	}


	@FXML
	public void newGame(){
		System.out.println("New Game");
	}

	@FXML
	public void playPause(){
		System.out.println("Play Pause");
		gameLoop.play();
	}

	@FXML
	public void showHighScore(){
		System.out.println("Show High Score");
	}

	@FXML
	public void exit(){
		System.out.println("Exit");
		System.exit(0);
	}

}
