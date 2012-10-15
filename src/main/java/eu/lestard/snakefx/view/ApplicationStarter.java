package eu.lestard.snakefx.view;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This Class starts the main application. It receives the primary Stage
 * from the JavaFX framework and creates the main Window.
 *
 * @author manuel.mauky
 *
 */
public class ApplicationStarter{

	private Scene mainScene;

	/**
	 * @param mainScene the root scene element for the application
	 */
	public ApplicationStarter(Scene mainScene){
		this.mainScene = mainScene;
	}

	/**
	 * @param primaryStage the primary stage for the JavaFx application.
	 */
	public void start(Stage primaryStage) {
		primaryStage.setTitle("SnakeFX");

		primaryStage.setScene(mainScene);

		primaryStage.setResizable(false);
		primaryStage.show();
	}


}
