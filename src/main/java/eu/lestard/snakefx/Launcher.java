package eu.lestard.snakefx;

import eu.lestard.snakefx.inject.DependencyInjector;
import eu.lestard.snakefx.util.FxmlFactory;
import eu.lestard.snakefx.util.KeyboardHandler;
import eu.lestard.snakefx.util.PopupDialogHelper;
import eu.lestard.snakefx.view.FXMLFile;
import eu.lestard.snakefx.viewmodel.ViewModel;
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


        PopupDialogHelper popupDialogHelper = new PopupDialogHelper(fxmlFactory);


        ViewModel viewModel = dependencyInjector.get(ViewModel.class);

        Stage aboutStage = popupDialogHelper.createModalDialog(viewModel.aboutWindowOpen, primaryStage, FXMLFile.ABOUT);

        Stage highScoreStage = popupDialogHelper.createModalDialog(viewModel.highscoreWindowOpen, primaryStage, FXMLFile.HIGHSCORE);

        popupDialogHelper.createModalDialog(viewModel.newHighscoreWindowOpen, highScoreStage, FXMLFile.NEW_HIGHSCORE);


        primaryStage.setTitle("SnakeFX");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
