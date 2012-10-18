package eu.lestard.snakefx.highscore;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HighScorePersistence {

	private static final Charset ENCODING = StandardCharsets.UTF_8;

    private Path filepath;

    public HighScorePersistence(Path filepath) {
    	this.filepath = filepath;
    }

    public void persistHighScores(List<HighScoreEntry> highScoreList){
    	try(BufferedWriter writer = Files.newBufferedWriter(filepath, ENCODING)){
    		for(HighScoreEntry e : highScoreList){
    			String csvLine = entryToCsv(e);

    			writer.write(csvLine);
    			writer.newLine();
    		}
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    }



    public List<HighScoreEntry> loadHighScores() {
    	List<HighScoreEntry> resultList = new ArrayList<HighScoreEntry>();

    	try{

	    	File file = filepath.toFile();
	    	if(!file.exists()){
	    		file.createNewFile();
	    	}

    	}catch(IOException e){
    		System.err.println("Can't create file for highscore");
    		e.printStackTrace();
    	}


    	try(Scanner scanner = new Scanner(filepath, ENCODING.name())){
    		while(scanner.hasNextLine()){
    			HighScoreEntry entry = csvToEntry(scanner.nextLine());
    			if(entry != null){
    				resultList.add(entry);
    			}
    		}
    	}catch(IOException e){
    		e.printStackTrace();
    	}

        return resultList;
    }

    /**
     * Create a CSV line representing the given {@link HighScoreEntry} instance.
     *
     * At the end of the line a semicolon is added.
     *
     * Comma and semicolon in the playername are replaced with a space so that
     * "My,cool;Playername" will result in "My cool Playername".
     *
     * @param entry
     * @return
     */
    protected String entryToCsv(HighScoreEntry entry) {
        StringBuilder csvBuilder = new StringBuilder();

        csvBuilder.append(entry.getRanking());
        csvBuilder.append(",");

        String name = entry.getPlayername();

        name = name.replaceAll(",", " ");
        name = name.replaceAll(";", " ");

        csvBuilder.append(name);
        csvBuilder.append(",");
        csvBuilder.append(entry.getPoints());
        csvBuilder.append(";");

        return csvBuilder.toString();
    }

    /**
     * Creates an instance of {@link HighScoreEntry} for the given CSV line.
     * The last character of the csv line can be a semicolon but this is not a must.
     * <br/>
     *
     * When there is an error in the csv string, <code>null</code> is returned.
     *
     * Some possible errors:
     * <ul>
     * 	<li>number for ranking or points contains illegal characters</li>
     * 	<li>there are too many values (separated by comma)</li>
     * 	<li>there are less then 3 values</li>
     *  <li>there is a semicolon in another place then the last character</li>
     * </ul>
     *
     * Superfluous spaces in the csv line are removed before the transformation.
     * For the playername only spaces in the middle of the name are permitted.
     * Leading and trailing spaces in the playername are removed:
     *
     * <code>"  my cool playername   "</code> will result in <code>"my cool playername"</code>.
     *
     * <br/>
     *
     * When the values for ranking or points are floating point numbers, the values
     * are converted to integers without rounding.
     * <br/>
     *
     *  Both <code>"1.0034"</code> and <code>"1.9993"</code> will result in 1.
     *	<br/>
     *
     *  When csvLine is <code>null</code> then <code>null</code> is returned.
     *
     */
    protected HighScoreEntry csvToEntry(String csvLine) {
        if(csvLine == null){
            return null;
        }

        if(csvLine.contains(";") && !csvLine.endsWith(";")){
            return null;
        }

        csvLine = csvLine.replaceAll(";", "");

        String[] splitByComma = csvLine.split(",");

        if(splitByComma.length != 3){
            return null;
        }

        int ranking = 0;
        int points = 0;

        try{
            ranking = (int)Double.parseDouble(splitByComma[0]);
            points = (int) Double.parseDouble(splitByComma[2]);
        }catch (NumberFormatException nfe){
            return null;
        }

        String playername = splitByComma[1].trim();

        return new HighScoreEntry(ranking, playername, points);
    }
}
