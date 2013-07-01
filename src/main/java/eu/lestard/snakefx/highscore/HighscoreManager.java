package eu.lestard.snakefx.highscore;

import static eu.lestard.snakefx.config.IntegerConfig.MAX_SCORE_COUNT;

import java.util.ArrayList;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

import com.sun.javafx.collections.ObservableListWrapper;

import eu.lestard.snakefx.config.IntegerConfig;

/**
 * The purpose of the HighscoreManager is to add new highscore entries and to
 * verify that there are only as many entries in the highscore list as defined
 * in {@link IntegerConfig#MAX_SCORE_COUNT}.
 * 
 * @author manuel.mauky
 * 
 */
public class HighscoreManager {

	private final ListProperty<HighScoreEntry> highScoreEntries = new SimpleListProperty<>(
			new ObservableListWrapper<HighScoreEntry>(new ArrayList<HighScoreEntry>()));

	private final HighscoreDao dao;

	public HighscoreManager(final HighscoreDao highScoreDao) {
		dao = highScoreDao;

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
