package eu.lestard.snakefx.view.presenter;

import eu.lestard.snakefx.highscore.HighScoreEntry;
import eu.lestard.snakefx.highscore.HighscoreManager;
import eu.lestard.snakefx.viewmodel.CentralViewModel;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import static eu.lestard.snakefx.config.Config.*;

public class HighscorePresenter {

    @FXML
    private TableView<HighScoreEntry> tableView;

    private final ListProperty<HighScoreEntry> highScoreEntries = new SimpleListProperty<>();

    private CentralViewModel viewModel;

    public HighscorePresenter(CentralViewModel viewModel, HighscoreManager highscoreManager) {
        this.viewModel = viewModel;
        viewModel.collision.addListener((observable, oldValue, collisionHappend) -> {
            if (collisionHappend) {
                gameFinished();
            }
        });

        this.highScoreEntries.bind(highscoreManager.highScoreEntries());
    }

    public ListProperty<HighScoreEntry> highScoreEntries() {
        return highScoreEntries;
    }

    public void gameFinished() {
        final int points = viewModel.points.get();

        final int size = highScoreEntries.size();

        if (size < MAX_SCORE_COUNT.get()) {
            viewModel.newHighscoreWindowOpen.set(true);
        } else {
            // check whether the last entry on the list has more points then the
            // current game

            if (highScoreEntries.get(size - 1).getPoints() < points) {
                viewModel.newHighscoreWindowOpen.set(true);
            }
        }
    }

    @FXML
    public void initialize() {
        tableView.setItems(highScoreEntries);
    }


}
