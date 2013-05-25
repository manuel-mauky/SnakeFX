package eu.lestard.snakefx.highscore;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HighscoreCsvDaoTest {

	private final static String filename = "highscores.csv";

	private final static String LINE_SEPARATOR = System.getProperty("line.separator");

	private Path filepath;

	private HighscoreCsvDao dao;

	@Before
	public void setup() {
		filepath = Paths.get("target", filename);

		dao = new HighscoreCsvDao(filepath);
	}

	@After
	public void after() {
		final File file = filepath.toFile();

		if (file.exists()) {
			file.delete();
		}
	}



	@Test
	public void testPersist() {
		final List<HighScoreEntry> entries = new ArrayList<HighScoreEntry>();

		entries.add(new HighScoreEntry(1, "yoda", 402));
		entries.add(new HighScoreEntry(2, "luke", 212));
		entries.add(new HighScoreEntry(3, "jabba", 123));


		final File file = filepath.toFile();

		assertThat(file).doesNotExist();

		dao.persist(entries);

		assertThat(file).exists().isFile();

		final String expectedContent = "1,yoda,402;" + LINE_SEPARATOR + "2,luke,212;" + LINE_SEPARATOR
				+ "3,jabba,123;" + LINE_SEPARATOR;

		assertThat(file).hasContent(expectedContent);
	}


	@Test
	public void testPersistAlreadyExistingFileIsOverwritten() throws IOException {
		final List<String> csvLines = new ArrayList<String>();
		csvLines.add("1,yoda,123;");
		csvLines.add("2,luke,234;");

		Files.write(filepath, csvLines, StandardCharsets.UTF_8);

		final File file = filepath.toFile();
		assertThat(file).exists().isFile();
		final String expectedContentBefore = "1,yoda,123;" + LINE_SEPARATOR + "2,luke,234;" + LINE_SEPARATOR;
		assertThat(file).hasContent(expectedContentBefore);



		final List<HighScoreEntry> entries = new ArrayList<HighScoreEntry>();
		entries.add(new HighScoreEntry(3, "jabba", 402));

		dao.persist(entries);

		assertThat(file).exists().isFile();

		final String expectedContentAfter = "3,jabba,402;" + LINE_SEPARATOR;
		assertThat(file).hasContent(expectedContentAfter);
	}



	@Test
	public void testload() throws IOException {
		final List<String> csvLines = new ArrayList<String>();
		csvLines.add("1,yoda,123;");
		csvLines.add("2,luke,234;");

		Files.write(filepath, csvLines, StandardCharsets.UTF_8);

		final List<HighScoreEntry> loadedScores = dao.load();

		assertThat(loadedScores).hasSize(2);
		assertThat(loadedScores.get(0)).isEqualsToByComparingFields(new HighScoreEntry(1, "yoda", 123));
		assertThat(loadedScores.get(1)).isEqualsToByComparingFields(new HighScoreEntry(2, "luke", 234));

	}


	@Test
	public void testloadNoExistingFile() {

		final File file = filepath.toFile();

		assertThat(file).doesNotExist();

		final List<HighScoreEntry> loadedScores = dao.load();
		assertThat(loadedScores).isEmpty();

		assertThat(file).exists();


	}

	@Test
	public void testEntryToCsv() {
		final String csv1 = dao.entryToCsv(new HighScoreEntry(2, "yoda", 124));
		assertThat(csv1).isEqualTo("2,yoda,124;");

		final String csv2 = dao.entryToCsv(new HighScoreEntry(1, "the Imperator", 342));

		assertThat(csv2).isEqualTo("1,the Imperator,342;");
	}

	@Test
	public void testEntryToCsvInvalidValuesAreReplaced() {
		final String csv1 = dao.entryToCsv(new HighScoreEntry(1, "yoda,with comma", 123));

		assertThat(csv1).isEqualTo("1,yoda with comma,123;"); // comma is
																// replaced with
																// a space

		final String csv2 = dao.entryToCsv(new HighScoreEntry(2, "the;Imperator", 345));

		assertThat(csv2).isEqualTo("2,the Imperator,345;"); // semicolon is
															// replaced with a
															// space
	}

	@Test
	public void testCsvToEntry() {
		final String csv1 = "2,yoda,124";
		final HighScoreEntry expected1 = new HighScoreEntry(2, "yoda", 124);

		assertThat(dao.csvToEntry(csv1)).isEqualsToByComparingFields(expected1);


		final String csv2 = "1,the Imperator,342";
		final HighScoreEntry expected2 = new HighScoreEntry(1, "the Imperator", 342);

		assertThat(dao.csvToEntry(csv2)).isEqualsToByComparingFields(expected2);
	}

	@Test
	public void testCsvToEntryWrongNumberFormat() {
		final String csv1 = "wrong, yoda, 123";

		assertThat(dao.csvToEntry(csv1)).isNull();


		final String csv2 = "12, yoda, 123w";

		assertThat(dao.csvToEntry(csv2)).isNull();
	}

	@Test
	public void testCsvToEntryWrongNumberOfValues() {
		final String csvToManyValues = "1,yoda,123,456";
		assertThat(dao.csvToEntry(csvToManyValues)).isNull();

		final String csvToLessValues = "1,yoda";
		assertThat(dao.csvToEntry(csvToLessValues)).isNull();
	}

	@Test
	public void testCsvToEntrySemicolonInPlayername() {
		final String csv = "1,the;imperator,123";

		assertThat(dao.csvToEntry(csv)).isNull();
	}

	@Test
	public void testCsvToEntrySemicolonAsLastCharacter() {
		final String csv = "1,yoda,123;";
		final HighScoreEntry expected = new HighScoreEntry(1, "yoda", 123);

		assertThat(dao.csvToEntry(csv)).isEqualsToByComparingFields(expected);
	}

	@Test
	public void testCsvToEntrySuperfluousSpacesAreNoProblem() {
		final String csv = " 1 ,yoda, 123 ";

		final HighScoreEntry expected = new HighScoreEntry(1, "yoda", 123);

		assertThat(dao.csvToEntry(csv)).isEqualsToByComparingFields(expected);
	}

	@Test
	public void testCsvToEntryLeadingAndTrailingSpacesInPlayernameAreRemoved() {
		final String csv = "1,   the Imperator  ,123";

		final HighScoreEntry expected = new HighScoreEntry(1, "the Imperator", 123);

		assertThat(dao.csvToEntry(csv)).isEqualsToByComparingFields(expected);
	}

	@Test
	public void testCsvToEntryFloatingPointNumbersAreCutOf() {
		final String csv = "1.0034,yoda, 9.9999";
		final HighScoreEntry expected = new HighScoreEntry(1, "yoda", 9);

		assertThat(dao.csvToEntry(csv)).isEqualsToByComparingFields(expected);
	}


	@Test
	public void testCsvToEntryNullParam() {
		assertThat(dao.csvToEntry(null)).isNull();
	}
}
