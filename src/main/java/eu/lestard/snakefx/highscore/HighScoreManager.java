package eu.lestard.snakefx.highscore;

import static eu.lestard.snakefx.config.IntegerConfig.MAX_SCORE_COUNT;

import java.util.ArrayList;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

import com.sun.javafx.collections.ObservableListWrapper;

public class HighScoreManager {

	private final ListProperty<HighScoreEntry> highScoreEntries = new SimpleListProperty<>(
			new ObservableListWrapper<HighScoreEntry>(new ArrayList<HighScoreEntry>()));

	private final HighscoreDao dao;

	public HighScoreManager(final HighscoreDao highScorePersistence) {
		dao = highScorePersistence;

		highScoreEntries.setAll(dao.load());
	}

	public ListProperty<HighScoreEntry> highScoreEntries() {
		return highScoreEntries;
	}

	public void addScore(final String name, final int points) {
		final HighScoreEntry entry = new HighScoreEntry(1, name, points);

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

		dao.persist(highScoreEntries);
	}
}
