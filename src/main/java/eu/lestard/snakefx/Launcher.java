package eu.lestard.snakefx;

import de.saxsys.jfx.mvvm.api.MvvmFX;
import de.saxsys.jfx.mvvm.viewloader.ViewLoader;
import de.saxsys.jfx.mvvm.viewloader.ViewTuple;
import eu.lestard.snakefx.inject.DependencyInjector;
import eu.lestard.snakefx.util.KeyboardHandler;
import eu.lestard.snakefx.util.TriggerablePopup;
import eu.lestard.snakefx.view.highscore.HighscoreView;
import eu.lestard.snakefx.view.highscore.NewHighscoreView;
import eu.lestard.snakefx.view.main.MainView;
import eu.lestard.snakefx.view.main.MainViewModel;
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

        final ViewTuple<MainView, MainViewModel> viewTuple = viewLoader.loadViewTuple(MainView.class);

        Scene scene = new Scene(viewTuple.getView());
        scene.setOnKeyPressed(dependencyInjector.get(KeyboardHandler.class));

        CentralViewModel viewModel = dependencyInjector.get(CentralViewModel.class);

        TriggerablePopup highscorePopup = new TriggerablePopup(HighscoreView.class, primaryStage);
        highscorePopup.trigger().bindBidirectional(viewModel.highscoreWindowOpen);

        TriggerablePopup newHighscorePopup = new TriggerablePopup(NewHighscoreView.class, highscorePopup.getStage());
        newHighscorePopup.trigger().bindBidirectional(viewModel.newHighscoreWindowOpen);


        primaryStage.setTitle("SnakeFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
