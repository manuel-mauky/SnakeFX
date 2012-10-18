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

	private Stage primaryStage;

	/**
	 * @param mainScene the root scene element for the application
	 */
	public ApplicationStarter(Scene mainScene, Stage primaryStage){
		this.mainScene = mainScene;
		this.primaryStage = primaryStage;
	}

	/**
	 * @param primaryStage the primary stage for the JavaFx application.
	 */
	public void start() {
		primaryStage.setTitle("SnakeFX");

		primaryStage.setScene(mainScene);

		primaryStage.setResizable(false);
		primaryStage.show();
	}


}
