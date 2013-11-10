package eu.lestard.snakefx;

import eu.lestard.snakefx.inject.DependencyInjector;
import eu.lestard.snakefx.inject.FxmlFactory;
import eu.lestard.snakefx.view.FXMLFile;
import eu.lestard.snakefx.util.KeyboardHandler;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {

    public static void main(final String... args) {
        Application.launch(Launcher.class, args);
    }


    @Override
    public void start(final Stage primaryStage) {
        DependencyInjector dependencyInjector = new DependencyInjector();

        FxmlFactory fxmlFactory = new FxmlFactory(dependencyInjector);
        Scene scene = new Scene(fxmlFactory.getFxmlRoot(FXMLFile.MAIN));
        scene.setOnKeyPressed(dependencyInjector.get(KeyboardHandler.class));

        primaryStage.setTitle("SnakeFX");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
