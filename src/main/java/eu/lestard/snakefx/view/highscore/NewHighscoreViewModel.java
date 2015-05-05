package eu.lestard.snakefx.view.highscore;


import de.saxsys.mvvmfx.ViewModel;
import eu.lestard.snakefx.highscore.HighScoreEntry;
import eu.lestard.snakefx.highscore.HighscoreManager;
import eu.lestard.snakefx.viewmodel.CentralViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableStringValue;

import javax.inject.Singleton;

@Singleton
public class NewHighscoreViewModel implements ViewModel {

    private final HighscoreManager highscoreManager;
    private HighscoreViewModel highscoreViewModel;
    private final CentralViewModel centralViewModel;

    private StringProperty pointsLabelText = new SimpleStringProperty();

    private BooleanProperty errorMessageVisible = new SimpleBooleanProperty();

    public NewHighscoreViewModel(CentralViewModel centralViewModel, HighscoreManager highscoreManager, HighscoreViewModel highscoreViewModel){
        this.centralViewModel = centralViewModel;
        this.highscoreManager = highscoreManager;
        this.highscoreViewModel = highscoreViewModel;
    }

    public ObservableStringValue pointsLabelText(){
        return pointsLabelText;
    }

    public ObservableBooleanValue errorMessageVisible(){
        return errorMessageVisible;
    }

    public void addEntry(String playerName) {
        if (isNameValid(playerName)) {
            errorMessageVisible.set(false);
        } else {
            errorMessageVisible.set(true);
            return;
        }

        final HighScoreEntry highScoreEntry = highscoreManager.addScore(playerName, centralViewModel.points.get());
        highscoreViewModel.selectedEntry().setValue(highScoreEntry);


        centralViewModel.newHighscoreWindowOpen.set(false);
        centralViewModel.highscoreWindowOpen.set(true);

    }

    private boolean isNameValid(final String name) {
        final boolean invalid = name == null || name.isEmpty() || name.contains(",") || name.contains(";");
        return !invalid;
    }
}
