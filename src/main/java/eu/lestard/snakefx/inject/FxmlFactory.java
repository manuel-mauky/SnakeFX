package eu.lestard.snakefx.inject;

import java.io.IOException;
import java.io.InputStream;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * This factory can be used to load FXML documents and get the root element of
 * the given fxml document.
 * 
 * @author manuel.mauky
 * 
 */
public class FxmlFactory {

	/**
	 * Loads the fxml file with the given name and returns the root element.
	 * 
	 * The given controller is connected to the fxml.
	 * 
	 * @param filename
	 * @param controller
	 * @return
	 */
	public Parent getFxmlRoot(final String filename, final Object controller) {
		FXMLLoader fxmlLoader = new FXMLLoader();
		return getFxmlRoot(filename, controller, fxmlLoader);
	}

	protected Parent getFxmlRoot(final String filename,
			final Object controller, final FXMLLoader fxmlLoader) {
		if (controller == null) {
			throw new IllegalArgumentException(
					"the controller param must not be null");
		}

		fxmlLoader.setController(controller);
		InputStream is = null;

		try {
			is = controller.getClass().getResourceAsStream(filename);

			if (is == null) {
				throw new IllegalArgumentException("the fxml file wasn't found");
			}

			return (Parent) fxmlLoader.load(is);
		} catch (final IOException e) {
			throw new IllegalStateException("can't load FXML file:" + filename,
					e);
		}
	}



}
