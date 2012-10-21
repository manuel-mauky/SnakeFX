package eu.lestard.snakefx.view.controller;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import eu.lestard.snakefx.highscore.HighScoreEntry;

public class HighScoreController {

	private final int scoreCount;

	@FXML
	private TableView<HighScoreEntry> tableView;

	private final ReadOnlyIntegerProperty pointsProperty;

	private final Stage newScoreEntryStage;

	private ObservableList<HighScoreEntry> highScoreEntries = FXCollections
			.observableArrayList();



	public HighScoreController(final Stage newScoreEntryStage,
			final ReadOnlyIntegerProperty pointsProperty,
			final ObservableList<HighScoreEntry> highScoreEntries,
			final int scoreCount) {
		this.newScoreEntryStage = newScoreEntryStage;
		this.pointsProperty = pointsProperty;
		this.highScoreEntries = highScoreEntries;
		this.scoreCount = scoreCount;
	}

	public void gameFinished() {
		int points = pointsProperty.get();

		int size = highScoreEntries.size();

		if (size < scoreCount) {
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
