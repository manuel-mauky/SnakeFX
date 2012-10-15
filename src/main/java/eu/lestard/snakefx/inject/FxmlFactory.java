package eu.lestard.snakefx.inject;

import java.io.IOException;
import java.io.InputStream;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class FxmlFactory {

	private FXMLLoader fxmlLoader;

	public FxmlFactory(FXMLLoader fxmlLoader){
		this.fxmlLoader = fxmlLoader;
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
	public Parent getFxmlRoot(String filename, Object controller){
		if(controller == null){
			throw new IllegalArgumentException("the controller param must not be null");
		}

		fxmlLoader.setController(controller);
		InputStream is = null;

		try{
			is = controller.getClass().getResourceAsStream(filename);

			if(is == null){
				throw new IllegalArgumentException("the fxml file wasn't found");
			}

			return (Parent)fxmlLoader.load(is);
		}catch(IOException e){
			throw new IllegalStateException("can't load FXML file:" + filename, e);
		}
	}

}
