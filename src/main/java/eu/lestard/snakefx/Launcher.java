package eu.lestard.snakefx;

import de.saxsys.jfx.mvvm.api.MvvmFX;
import de.saxsys.jfx.mvvm.viewloader.ViewLoader;
import de.saxsys.jfx.mvvm.viewloader.ViewTuple;
import eu.lestard.snakefx.inject.DependencyInjector;
import eu.lestard.snakefx.util.FxmlFactory;
import eu.lestard.snakefx.util.KeyboardHandler;
import eu.lestard.snakefx.util.PopupDialogHelper;
import eu.lestard.snakefx.view.FXMLFile;
import eu.lestard.snakefx.view.Main;
import eu.lestard.snakefx.view.viewmodels.MainViewModel;
import eu.lestard.snakefx.viewmodel.CentralViewModel;
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

        MvvmFX.setCustomDependencyInjector(dependencyInjector);

        ViewLoader viewLoader = new ViewLoader();

        final ViewTuple<MainViewModel> viewTuple = viewLoader.loadViewTuple(Main.class);


        Scene scene = new Scene(viewTuple.getView());
        scene.setOnKeyPressed(dependencyInjector.get(KeyboardHandler.class));


        FxmlFactory fxmlFactory = new FxmlFactory(dependencyInjector);
        PopupDialogHelper popupDialogHelper = new PopupDialogHelper(fxmlFactory);


        CentralViewModel viewModel = dependencyInjector.get(CentralViewModel.class);

        Stage aboutStage = popupDialogHelper.createModalDialog(viewModel.aboutWindowOpen, primaryStage, FXMLFile.ABOUT);

        Stage highScoreStage = popupDialogHelper.createModalDialog(viewModel.highscoreWindowOpen, primaryStage, FXMLFile.HIGHSCORE);

        popupDialogHelper.createModalDialog(viewModel.newHighscoreWindowOpen, highScoreStage, FXMLFile.NEW_HIGHSCORE);


        primaryStage.setTitle("SnakeFX");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
