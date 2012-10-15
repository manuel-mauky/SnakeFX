package eu.lestard.snakefx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Launcher extends Application{

	public static void main(final String...args){
		Application.launch(Launcher.class, args);
	}


	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("SnakeFX");

		VBox vBox = new VBox();

		vBox.getChildren().add(new Label("hello world"));

		primaryStage.setScene(new Scene(vBox));

		primaryStage.show();
	}

}
