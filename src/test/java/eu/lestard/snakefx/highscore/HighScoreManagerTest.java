package eu.lestard.snakefx.highscore;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.extractProperty;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import eu.lestard.snakefx.viewmodel.ViewModel;

@Ignore
public class HighScoreManagerTest {
	private HighScoreManager scoreManager;

	private ObservableList<HighScoreEntry> highScoreEntries;

	private final static int MAX_SCORE_COUNT = 3;

	private HighScorePersistence persistenceMock;

	@Before
	public void setup() {
		highScoreEntries = FXCollections.observableArrayList();

		persistenceMock = mock(HighScorePersistence.class);

		scoreManager = new HighScoreManager(persistenceMock);
	}

	@Test
	public void testConstructor() {

		final List<HighScoreEntry> existingEntries = new ArrayList<HighScoreEntry>();
		final HighScoreEntry highScoreEntry = new HighScoreEntry(1, "yoda,", 14);
		existingEntries.add(highScoreEntry);

		when(persistenceMock.loadHighScores()).thenReturn(existingEntries);

		scoreManager = new HighScoreManager(persistenceMock);

		assertThat(highScoreEntries).hasSize(1);
		assertThat(highScoreEntries).contains(highScoreEntry);
	}

	@Test
	public void testScores() {
		scoreManager.addScore("yoda", 100);
		scoreManager.addScore("yoda", 213);
		scoreManager.addScore("luke", 143);

		assertThat(highScoreEntries).hasSize(3);

		assertThat(extractProperty("points", Integer.class).from(highScoreEntries))
				.containsSequence(213, 143, 100);
		assertThat(extractProperty("playername", String.class).from(highScoreEntries)).containsSequence("yoda",
				"luke", "yoda");

		scoreManager.addScore("jabba", 215);

		assertThat(highScoreEntries).hasSize(3);
		assertThat(extractProperty("points", Integer.class).from(highScoreEntries))
				.containsSequence(215, 213, 143).doesNotContain(100);
		assertThat(extractProperty("playername", String.class).from(highScoreEntries)).containsSequence("jabba",
				"yoda", "luke");

		verify(persistenceMock, times(4)).persistHighScores(highScoreEntries);

	}
}
