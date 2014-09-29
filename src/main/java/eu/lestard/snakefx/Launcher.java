package eu.lestard.snakefx;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.MvvmFX;
import de.saxsys.mvvmfx.ViewTuple;
import eu.lestard.easydi.EasyDI;
import eu.lestard.grid.GridModel;
import eu.lestard.snakefx.highscore.HighscoreDao;
import eu.lestard.snakefx.highscore.HighscoreJsonDao;
import eu.lestard.snakefx.util.KeyboardHandler;
import eu.lestard.snakefx.util.TriggerablePopup;
import eu.lestard.snakefx.view.highscore.HighscoreView;
import eu.lestard.snakefx.view.highscore.NewHighscoreView;
import eu.lestard.snakefx.view.main.MainView;
import eu.lestard.snakefx.view.main.MainViewModel;
import eu.lestard.snakefx.viewmodel.CentralViewModel;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {


    public static void main(final String... args) {
        Application.launch(Launcher.class, args);
    }

    private TriggerablePopup newHighscorePopup;
    private TriggerablePopup highscorePopup;

    @Override
    public void start(final Stage primaryStage) {

        EasyDI easyDI = new EasyDI();
        easyDI.bindProvider(HostServices.class, this::getHostServices);
        easyDI.bindInterface(HighscoreDao.class, HighscoreJsonDao.class);

        easyDI.markAsSingleton(GridModel.class);

        MvvmFX.setCustomDependencyInjector(type->easyDI.getInstance(type));

        final ViewTuple<MainView, MainViewModel> viewTuple = FluentViewLoader.fxmlView(MainView.class).load();

        Scene scene = new Scene(viewTuple.getView());
        scene.setOnKeyPressed(easyDI.getInstance(KeyboardHandler.class));



        CentralViewModel viewModel = easyDI.getInstance(CentralViewModel.class);

        highscorePopup = new TriggerablePopup(HighscoreView.class, primaryStage);
        highscorePopup.trigger().bindBidirectional(viewModel.highscoreWindowOpen);

        newHighscorePopup = new TriggerablePopup(NewHighscoreView.class, highscorePopup.getStage());
        newHighscorePopup.trigger().bindBidirectional(viewModel.newHighscoreWindowOpen);

        primaryStage.setTitle("SnakeFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
