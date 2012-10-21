package eu.lestard.snakefx.config;

/**
 * This enum represents configuration parameters of type String.
 * 
 * @author manuel.mauky
 */
public enum StringConfig {

	/**
	 * The filename of the fxml file for the main window.
	 */
	FXML_FILENAME_MAIN("/fxml/main.fxml"),

	/**
	 * The filename of the fxml file for the highscore window.
	 */
	FXML_FILENAME_HIGHSCORE("/fxml/highscore.fxml"),

	/**
	 * The filename of the fxml file for the new score entry window.
	 */
	FXML_FILENAME_NEW_SCORE_ENTRY("/fxml/newScoreEntry.fxml"),

	/**
	 * The filepath where the highscore should be saved.
	 */
	HIGH_SCORE_FILEPATH("highscores"),

	;


	private StringConfig(final String value) {
		this.value = value;
	}

	private String value;

	public String get() {
		return value;
	}

}
