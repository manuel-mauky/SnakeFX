package eu.lestard.snakefx.view.highscore;


import de.saxsys.mvvmfx.ViewModel;
import eu.lestard.snakefx.highscore.HighScoreEntry;
import eu.lestard.snakefx.highscore.HighscoreManager;
import eu.lestard.snakefx.viewmodel.CentralViewModel;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;

import javax.inject.Singleton;

import static eu.lestard.snakefx.config.Config.MAX_SCORE_COUNT;

@Singleton
public class HighscoreViewModel implements ViewModel {

    private final CentralViewModel centralViewModel;
    private ListProperty<HighScoreEntry> highScoreEntries = new SimpleListProperty<>();

    private ObjectProperty<HighScoreEntry> selectedEntry = new SimpleObjectProperty<>();

    public HighscoreViewModel(CentralViewModel centralViewModel, HighscoreManager highscoreManager) {
        this.centralViewModel = centralViewModel;

        centralViewModel.collision.addListener((observable, oldValue, collisionHappened) -> {
            if(collisionHappened){
                gameFinished();
            }
        });

        this.highScoreEntries.bind(highscoreManager.highScoreEntries());
    }

    void gameFinished(){
        final int points = centralViewModel.points.get();

        final int size = highScoreEntries.size();

        if (size < MAX_SCORE_COUNT.get()) {
            centralViewModel.newHighscoreWindowOpen.set(true);
        } else {
            // check whether the last entry on the list has more points then the
            // current game

            if (highScoreEntries.get(size - 1).getPoints() < points) {
                centralViewModel.newHighscoreWindowOpen.set(true);
            }
        }
    }

    public ObservableList<HighScoreEntry> highScoreEntries(){
        return highScoreEntries;
    }

    public ObjectProperty<HighScoreEntry> selectedEntry() {
        return selectedEntry;
    }
}
