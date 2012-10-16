package eu.lestard.snakefx.view;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.ChoiceBox;
import eu.lestard.snakefx.core.Field;
import eu.lestard.snakefx.core.FoodGenerator;
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

	private FoodGenerator foodGenerator;

	public MainController(Grid grid,
				Snake snake,
				GameLoop gameLoop,
				SpeedChangeController speedChangeController,
				FoodGenerator foodGenerator){
		this.grid = grid;
		this.snake = snake;
		this.gameLoop = gameLoop;
		this.speedChangeController = speedChangeController;
		this.foodGenerator = foodGenerator;
	}

	@FXML
	public void initialize(){
		grid.init();

		snake.init();

		gameLoop.init();

		speedChangeController.init(speedChoiceBox);

		foodGenerator.generateFood();

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
