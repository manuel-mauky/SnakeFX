package eu.lestard.snakefx.view;

import java.net.URL;

public enum FXMLFile {


	MAIN("main.fxml"),

	MENU("menu.fxml"),

	PANEL("panel.fxml"),

	ABOUT("about.fxml"),

	HIGHSCORE("highscore.fxml"),

	NEW_HIGHSCORE("newHighscore.fxml")

	;

	private static final String BASE_DIR = "fxml";

	private String filename;

	private URL base;
	private URL url;

	private FXMLFile(final String path) {
		filename = path;

		base = this.getClass().getClassLoader().getResource(BASE_DIR);
		if (base == null) {
			throw new IllegalStateException("Can't find the base directory of the fxml files [" + base + "]");
		}

		final String fxmlFilePath = BASE_DIR + "/" + path;
		url = this.getClass().getClassLoader().getResource(fxmlFilePath);

		if (url == null) {
			throw new IllegalStateException("Can't find the fxml file [" + fxmlFilePath + "]");
		}
	}

	public String filename() {
		return filename;
	}

	public URL url() {
		return url;
	}

	public URL base() {
		return base;
	}
}
