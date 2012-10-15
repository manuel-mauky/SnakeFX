package eu.lestard.snakefx.view;

import javafx.fxml.FXML;

/**
 * Controller class for the main.fxml file.
 * @author manuel.mauky
 *
 */
public class MainController {

	@FXML
	public void newGame(){
		System.out.println("New Game");
	}

	@FXML
	public void playPause(){
		System.out.println("Play Pause");
	}

	@FXML
	public void showHighScore(){
		System.out.println("Show High Score");
	}

	@FXML
	public void exit(){
		System.out.println("Exit");
	}

}
