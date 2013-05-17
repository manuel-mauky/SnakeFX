package eu.lestard.snakefx.highscore;

import static eu.lestard.snakefx.config.IntegerConfig.MAX_SCORE_COUNT;

import java.util.ArrayList;

import com.sun.javafx.collections.ObservableListWrapper;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class HighScoreManager {

	private final ListProperty<HighScoreEntry> highScoreEntries = new SimpleListProperty<>(
			new ObservableListWrapper<HighScoreEntry>(new ArrayList<HighScoreEntry>()));

	private final HighScorePersistence persistence;

	public HighScoreManager(HighScorePersistence highScorePersistence) {
		persistence = highScorePersistence;

		highScoreEntries.setAll(persistence.loadHighScores());
	}

	public ListProperty<HighScoreEntry> highScoreEntries() {
		return highScoreEntries;
	}

	public void addScore(String name, int points) {
		HighScoreEntry entry = new HighScoreEntry(1, name, points);

		highScoreEntries.add(entry);

		updateList();
	}

	private void updateList() {
		FXCollections.sort(highScoreEntries);

		for (int i = 0; i < highScoreEntries.size(); i++) {
			if (i < MAX_SCORE_COUNT.get()) {
				highScoreEntries.get(i).setRanking(i + 1);
			} else {
				highScoreEntries.remove(i);
			}
		}

		persistence.persistHighScores(highScoreEntries);
	}
}
