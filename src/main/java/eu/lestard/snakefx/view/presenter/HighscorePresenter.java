package eu.lestard.snakefx.view.presenter;

import static eu.lestard.snakefx.config.IntegerConfig.MAX_SCORE_COUNT;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import eu.lestard.snakefx.highscore.HighScoreEntry;

public class HighscorePresenter {

	@FXML
	private TableView<HighScoreEntry> tableView;

	private final Stage newScoreEntryStage;
	private final ListProperty<HighScoreEntry> highScoreEntries = new SimpleListProperty<>();
	private final IntegerProperty pointsProperty = new SimpleIntegerProperty();


	public HighscorePresenter(final Stage newScoreEntryStage) {
		this.newScoreEntryStage = newScoreEntryStage;
	}

	public IntegerProperty pointsProperty() {
		return pointsProperty;
	}

	public ListProperty<HighScoreEntry> highScoreEntries() {
		return highScoreEntries;
	}

	public void gameFinished() {
		final int points = pointsProperty.get();

		final int size = highScoreEntries.size();

		if (size < MAX_SCORE_COUNT.get()) {
			newScoreEntryStage.show();
		} else {
			// check whether the last entry on the list has more points then the
			// current game

			if (highScoreEntries.get(size - 1).getPoints() < points) {
				newScoreEntryStage.show();
			}
		}
	}

	@FXML
	public void initialize() {
		tableView.setItems(highScoreEntries);
	}


}
