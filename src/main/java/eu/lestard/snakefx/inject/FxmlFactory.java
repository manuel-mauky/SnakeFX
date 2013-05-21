package eu.lestard.snakefx.inject;

import java.io.IOException;
import java.io.InputStream;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.util.Callback;
import eu.lestard.snakefx.view.FXMLFile;

/**
 * This factory can be used to load FXML documents and get the root element of
 * the given fxml document.
 * 
 * @author manuel.mauky
 * 
 */
public class FxmlFactory {


	private final ControllerInjector controllerInjector;


	public FxmlFactory(ControllerInjector injector) {
		controllerInjector = injector;
	}

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

	public Parent getFxmlRoot(FXMLFile file) {
		FXMLLoader loader = new FXMLLoader(file.url());

		loader.setControllerFactory(controllerInjector);

		try {
			loader.load();
		} catch (IOException e) {
			throw new IllegalStateException("Can't load FXML file [" + file.url() + "]", e);
		}

		return loader.getRoot();
	}


	protected Parent getFxmlRoot(final String filename, final Object controller, final FXMLLoader fxmlLoader) {
		if (controller == null) {
			throw new IllegalArgumentException("the controller param must not be null");
		}

		fxmlLoader.setController(controller);
		fxmlLoader.setLocation(this.getClass().getClassLoader().getResource("fxml/"));

		InputStream is = null;

		try {
			is = controller.getClass().getResourceAsStream(filename);

			if (is == null) {
				throw new IllegalArgumentException("the fxml file wasn't found");
			}
			return (Parent) fxmlLoader.load(is);
		} catch (final IOException e) {
			throw new IllegalStateException("can't load FXML file:" + filename, e);
		}
	}



}
