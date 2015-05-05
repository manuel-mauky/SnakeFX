package eu.lestard.snakefx.highscore;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

import javax.inject.Singleton;
import java.util.ArrayList;

import static eu.lestard.snakefx.config.Config.*;

/**
 * The purpose of the HighscoreManager is to add new highscore entries and to
 * verify that there are only as many entries in the highscore list as defined
 * in {@link eu.lestard.snakefx.config.Config#MAX_SCORE_COUNT}.
 *
 * @author manuel.mauky
 */
@Singleton
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

    public HighScoreEntry addScore(final String name, final int points) {
        final HighScoreEntry entry = new HighScoreEntry(1, name, points);

        highScoreEntries.add(entry);

        updateList();

        return entry;
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
