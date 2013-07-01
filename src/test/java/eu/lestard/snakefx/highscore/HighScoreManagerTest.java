package eu.lestard.snakefx.highscore;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.extractProperty;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

import eu.lestard.snakefx.config.IntegerConfig;

public class HighScoreManagerTest {
	private HighscoreManager scoreManager;

	private HighscoreDao daoMock;

	@Before
	public void setup() {
		daoMock = mock(HighscoreDao.class);

		scoreManager = new HighscoreManager(daoMock);

		// Change the config value to 3 for easier testing.
		Whitebox.setInternalState(IntegerConfig.MAX_SCORE_COUNT, "value", 3);
	}

	@Test
	public void testConstructor() {

		final List<HighScoreEntry> existingEntries = new ArrayList<HighScoreEntry>();
		final HighScoreEntry highScoreEntry = new HighScoreEntry(1, "yoda,", 14);
		existingEntries.add(highScoreEntry);

		when(daoMock.load()).thenReturn(existingEntries);

		scoreManager = new HighscoreManager(daoMock);

		assertThat(scoreManager.highScoreEntries()).hasSize(1);
		assertThat(scoreManager.highScoreEntries()).contains(highScoreEntry);
	}

	@Test
	public void testScores() {
		scoreManager.addScore("yoda", 100);
		scoreManager.addScore("yoda", 213);
		scoreManager.addScore("luke", 143);

		assertThat(scoreManager.highScoreEntries()).hasSize(3);

		assertThat(extractProperty("points", Integer.class).from(scoreManager.highScoreEntries()))
				.containsSequence(213, 143, 100);
		assertThat(extractProperty("playername", String.class).from(scoreManager.highScoreEntries()))
				.containsSequence("yoda", "luke", "yoda");

		scoreManager.addScore("jabba", 215);

		assertThat(scoreManager.highScoreEntries()).hasSize(3);
		assertThat(extractProperty("points", Integer.class).from(scoreManager.highScoreEntries()))
				.containsSequence(215, 213, 143).doesNotContain(100);
		assertThat(extractProperty("playername", String.class).from(scoreManager.highScoreEntries()))
				.containsSequence("jabba", "yoda", "luke");

		verify(daoMock, times(4)).persist(scoreManager.highScoreEntries());
	}
}
