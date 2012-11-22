package eu.lestard.snakefx.view.controller;

import javafx.fxml.FXML;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class AboutController {


	private static final String URL_LESTARD_EU = "http://www.lestard.eu";
	private static final String URL_GPL = "http://www.gnu.org/licenses/gpl.html";

	@FXML
	public void homepageLink() {
		openLink(URL_LESTARD_EU);
	}

	@FXML
	public void licenseLink() {
		openLink(URL_GPL);
	}

	private void openLink(String urlAsString) {
		if (Desktop.isDesktopSupported()) {
			Desktop desktop = Desktop.getDesktop();

			if (desktop.isSupported(Desktop.Action.BROWSE)) {
				try {
					desktop.browse(new URI(urlAsString));
				} catch (IOException | URISyntaxException e) {
					System.err.println("Can't open the URL:" + urlAsString);
					e.printStackTrace();
					return;
				}
			}
		}
		System.err.println("Can't open the URL:" + urlAsString);
	}
}
