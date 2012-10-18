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

public class HighScorePersistenceIntegrationTest {

    private final static String filename = "highscores.csv";

    private final static String LINE_SEPARATOR = System.getProperty("line.separator");

    private Path filepath;

    private HighScorePersistence persistence;

    @Before
    public void setup(){
    	filepath = Paths.get("target", filename);

        persistence = new HighScorePersistence(filepath);
    }

    @After
    public void after(){
    	File file = filepath.toFile();

    	if(file.exists()){
    		file.delete();
    	}
    }



    @Test
    public void testPersist(){
        List<HighScoreEntry> entries = new ArrayList<HighScoreEntry>();

        entries.add(new HighScoreEntry(1,"yoda", 402));
        entries.add(new HighScoreEntry(2, "luke", 212));
        entries.add(new HighScoreEntry(3,"jabba", 123));


        File file = filepath.toFile();

        assertThat(file).doesNotExist();

        persistence.persistHighScores(entries);

        assertThat(file).exists().isFile();

        String expectedContent = "1,yoda,402;" + LINE_SEPARATOR + "2,luke,212;" + LINE_SEPARATOR +"3,jabba,123;" + LINE_SEPARATOR;

        assertThat(file).hasContent(expectedContent);
    }


    @Test
    public void testPersistAlreadyExistingFileIsOverwritten() throws IOException{
    	List<String> csvLines = new ArrayList<String>();
    	csvLines.add("1,yoda,123;");
    	csvLines.add("2,luke,234;");

    	Files.write(filepath, csvLines, StandardCharsets.UTF_8);

    	File file = filepath.toFile();
    	assertThat(file).exists().isFile();
    	String expectedContentBefore = "1,yoda,123;" + LINE_SEPARATOR + "2,luke,234;" + LINE_SEPARATOR;
    	assertThat(file).hasContent(expectedContentBefore);



    	List<HighScoreEntry> entries = new ArrayList<HighScoreEntry>();
    	entries.add(new HighScoreEntry(3,"jabba", 402));

    	persistence.persistHighScores(entries);

    	assertThat(file).exists().isFile();

    	String expectedContentAfter = "3,jabba,402;" + LINE_SEPARATOR;
    	assertThat(file).hasContent(expectedContentAfter);
    }



    @Test
    public void testLoadHighScores() throws IOException{
    	List<String> csvLines = new ArrayList<String>();
    	csvLines.add("1,yoda,123;");
    	csvLines.add("2,luke,234;");

    	Files.write(filepath, csvLines, StandardCharsets.UTF_8);

    	List<HighScoreEntry> loadedScores = persistence.loadHighScores();

    	assertThat(loadedScores).hasSize(2);
    	assertThat(loadedScores.get(0)).isEqualsToByComparingFields(new HighScoreEntry(1,"yoda", 123));
    	assertThat(loadedScores.get(1)).isEqualsToByComparingFields(new HighScoreEntry(2,"luke", 234));

    }


    @Test
    public void testLoadHighScoresNoExistingFile(){

    	File file = filepath.toFile();

    	assertThat(file).doesNotExist();

    	List<HighScoreEntry> loadedScores = persistence.loadHighScores();
    	assertThat(loadedScores).isEmpty();

    	assertThat(file).exists();


    }

    @Test
    public void testEntryToCsv(){
        String csv1 = persistence.entryToCsv(new HighScoreEntry(2, "yoda", 124));
        assertThat(csv1).isEqualTo("2,yoda,124;");

        String csv2 = persistence.entryToCsv(new HighScoreEntry(1, "the Imperator",342));

        assertThat(csv2).isEqualTo("1,the Imperator,342;");
    }

    @Test
    public void testEntryToCsvInvalidValuesAreReplaced(){
        String csv1 = persistence.entryToCsv(new HighScoreEntry(1, "yoda,with comma", 123));

        assertThat(csv1).isEqualTo("1,yoda with comma,123;"); // comma is replaced with a space

        String csv2 = persistence.entryToCsv(new HighScoreEntry(2, "the;Imperator", 345));

        assertThat(csv2).isEqualTo("2,the Imperator,345;"); // semicolon is replaced with a space
    }

    @Test
    public void testCsvToEntry(){
        String csv1 = "2,yoda,124";
        HighScoreEntry expected1 = new HighScoreEntry(2,"yoda", 124);

        assertThat(persistence.csvToEntry(csv1)).isEqualsToByComparingFields(expected1);


        String csv2 = "1,the Imperator,342";
        HighScoreEntry expected2 = new HighScoreEntry(1,"the Imperator", 342);

        assertThat(persistence.csvToEntry(csv2)).isEqualsToByComparingFields(expected2);
    }

    @Test
    public void testCsvToEntryWrongNumberFormat(){
        String csv1 = "wrong, yoda, 123";

        assertThat(persistence.csvToEntry(csv1)).isNull();


        String csv2 = "12, yoda, 123w";

        assertThat(persistence.csvToEntry(csv2)).isNull();
    }

    @Test
    public void testCsvToEntryWrongNumberOfValues(){
        String csvToManyValues = "1,yoda,123,456";
        assertThat(persistence.csvToEntry(csvToManyValues)).isNull();

        String csvToLessValues = "1,yoda";
        assertThat(persistence.csvToEntry(csvToLessValues)).isNull();
    }

    @Test
    public void testCsvToEntrySemicolonInPlayername(){
    	String csv = "1,the;imperator,123";

    	assertThat(persistence.csvToEntry(csv)).isNull();
    }

    @Test
    public void testCsvToEntrySemicolonAsLastCharacter(){
    	String csv = "1,yoda,123;";
    	HighScoreEntry expected = new HighScoreEntry(1,"yoda",123);

    	assertThat(persistence.csvToEntry(csv)).isEqualsToByComparingFields(expected);
    }

    @Test
    public void testCsvToEntrySuperfluousSpacesAreNoProblem(){
    	String csv = " 1 ,yoda, 123 ";

    	HighScoreEntry expected = new HighScoreEntry(1,"yoda",123);

    	assertThat(persistence.csvToEntry(csv)).isEqualsToByComparingFields(expected);
    }

    @Test
    public void testCsvToEntryLeadingAndTrailingSpacesInPlayernameAreRemoved(){
    	String csv = "1,   the Imperator  ,123";

    	HighScoreEntry expected = new HighScoreEntry(1,"the Imperator", 123);

    	assertThat(persistence.csvToEntry(csv)).isEqualsToByComparingFields(expected);
    }

    @Test
    public void testCsvToEntryFloatingPointNumbersAreCutOf(){
    	String csv = "1.0034,yoda, 9.9999";
    	HighScoreEntry expected = new HighScoreEntry(1, "yoda", 9);

    	assertThat(persistence.csvToEntry(csv)).isEqualsToByComparingFields(expected);
    }


    @Test
    public void testCsvToEntryNullParam(){
    	assertThat(persistence.csvToEntry(null)).isNull();
    }
}
