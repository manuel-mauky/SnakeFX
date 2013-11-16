package eu.lestard.snakefx.view.presenter;

import static eu.lestard.snakefx.config.IntegerConfig.MAX_SCORE_COUNT;

import eu.lestard.snakefx.highscore.HighscoreManager;
import eu.lestard.snakefx.viewmodel.ViewModel;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import eu.lestard.snakefx.highscore.HighScoreEntry;

public class HighscorePresenter {

	@FXML
	private TableView<HighScoreEntry> tableView;

	private final ListProperty<HighScoreEntry> highScoreEntries = new SimpleListProperty<>();

    private ViewModel viewModel;

	public HighscorePresenter(ViewModel viewModel, HighscoreManager highscoreManager) {
        this.viewModel = viewModel;

        viewModel.collision.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean collisionHappend) {
                if(collisionHappend){
                    gameFinished();
                }
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
