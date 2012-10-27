package eu.lestard.snakefx;

import javafx.application.Application;
import javafx.stage.Stage;
import eu.lestard.snakefx.inject.MainDependencyInjector;
import eu.lestard.snakefx.view.ApplicationStarter;

public class Launcher extends Application {

	public static void main(final String... args) {
		Application.launch(Launcher.class, args);
	}


	@Override
	public void start(final Stage primaryStage) {
		MainDependencyInjector mainInjector = new MainDependencyInjector(
				primaryStage);

		ApplicationStarter starter = mainInjector.getApplicationStarter();

		starter.start();
	}

}
