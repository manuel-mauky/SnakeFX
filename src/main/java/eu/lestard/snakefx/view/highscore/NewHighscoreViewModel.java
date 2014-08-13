package eu.lestard.snakefx.view.highscore;


import de.saxsys.mvvmfx.ViewModel;
import eu.lestard.snakefx.highscore.HighscoreManager;
import eu.lestard.snakefx.viewmodel.CentralViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableStringValue;

public class NewHighscoreViewModel implements ViewModel {

    private final HighscoreManager highscoreManager;
    private final CentralViewModel centralViewModel;

    private StringProperty pointsLabelText = new SimpleStringProperty();

    private BooleanProperty errorMessageVisible = new SimpleBooleanProperty();

    public NewHighscoreViewModel(CentralViewModel centralViewModel, HighscoreManager highscoreManager){
        this.centralViewModel = centralViewModel;
        this.highscoreManager = highscoreManager;
    }

    public ObservableStringValue pointsLabelText(){
        return pointsLabelText;
    }

    public ObservableBooleanValue errorMessageVisible(){
        return errorMessageVisible;
    }

    public void addEntry(String playerName) {
        if (!isNameValid(playerName)) {
            errorMessageVisible.set(true);
            return;
        } else {
            errorMessageVisible.set(false);
        }

        highscoreManager.addScore(playerName, centralViewModel.points.get());
        centralViewModel.newHighscoreWindowOpen.set(false);
        centralViewModel.highscoreWindowOpen.set(true);
    }

    private boolean isNameValid(final String name) {
        if (name == null) {
            return false;
        }
        if (name.isEmpty()) {
            return false;
        }
        if (name.contains(",")) {
            return false;
        }

        if (name.contains(";")) {
            return false;
        }

        return true;
    }
}
