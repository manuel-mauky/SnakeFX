package eu.lestard.snakefx.view.menu;


import de.saxsys.mvvmfx.ViewModel;
import eu.lestard.snakefx.core.NewGameFunction;
import eu.lestard.snakefx.viewmodel.CentralViewModel;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import javax.inject.Singleton;
import java.util.function.Consumer;

@Singleton
public class MenuViewModel implements ViewModel {

    private final CentralViewModel centralViewModel;
    private final Consumer<?> newGameFunction;

    private BooleanProperty aboutPopupVisible = new SimpleBooleanProperty();

    public MenuViewModel(final CentralViewModel centralViewModel, final NewGameFunction newGameFunction) {
        this.centralViewModel = centralViewModel;
        this.newGameFunction = newGameFunction;
    }

    public BooleanProperty aboutPopupVisible(){
        return aboutPopupVisible;
    }

    public void newGame(){
        newGameFunction.accept(null);
    }

    public void showHighscores(){
        centralViewModel.highscoreWindowOpen.set(true);
    }

    public void about(){
        aboutPopupVisible.set(true);
    }

    public void exit(){
        Platform.exit();
    }

}
