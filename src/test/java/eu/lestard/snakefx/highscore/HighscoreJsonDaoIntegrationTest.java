package eu.lestard.snakefx.highscore;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HighscoreJsonDaoIntegrationTest {

	private final static String filename = "highscore.json";

	private Path filepath;

	private HighscoreJsonDao dao;

	@Before
	public void setup() {
		filepath = Paths.get("target", filename);

		dao = new HighscoreJsonDao(filepath);
	}

	@After
	public void after() {
		final File file = filepath.toFile();

		if (file.exists()) {
			file.delete();
		}
	}

	@Test
	public void testPersistAndLoad() {
		final List<HighScoreEntry> entries = new ArrayList<>();

		entries.add(new HighScoreEntry(1, "yoda", 402));
		entries.add(new HighScoreEntry(2, "luke", 212));
		entries.add(new HighScoreEntry(3, "jabba", 123));

		final File file = filepath.toFile();

		assertThat(file).doesNotExist();

		dao.persist(entries);

		assertThat(file).exists().isFile();


		final List<HighScoreEntry> loadedEntries = dao.load();

		assertThat(loadedEntries).hasSize(3);
		assertThat(loadedEntries.get(0)).isEqualsToByComparingFields(new HighScoreEntry(1, "yoda", 402));
		assertThat(loadedEntries.get(1)).isEqualsToByComparingFields(new HighScoreEntry(2, "luke", 212));
		assertThat(loadedEntries.get(2)).isEqualsToByComparingFields(new HighScoreEntry(3, "jabba", 123));
	}
}
