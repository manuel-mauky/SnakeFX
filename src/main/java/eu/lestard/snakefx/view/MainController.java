package eu.lestard.snakefx.view;

import javafx.fxml.FXML;
import javafx.scene.Group;
import eu.lestard.snakefx.core.Field;
import eu.lestard.snakefx.core.GameLoop;
import eu.lestard.snakefx.core.Grid;
import eu.lestard.snakefx.core.Snake;

/**
 * Controller class for the main.fxml file.
 * @author manuel.mauky
 *
 */
public class MainController {

	@FXML
	private Group gridContainer;

	private Snake snake;

	private Grid grid;

	private GameLoop gameLoop;

	public MainController(Grid grid, Snake snake, GameLoop gameLoop){
		this.grid = grid;
		this.snake = snake;
		this.gameLoop = gameLoop;
	}

	@FXML
	public void initialize(){
		grid.init();

		snake.init();

		gameLoop.init();

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
