package eu.lestard.snakefx.inject;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import eu.lestard.snakefx.config.StringConfig;

/**
 * This factory can be used to create {@link Stage} instances for a given file
 * path to a fxml file and a controller for this fxml document.
 * 
 * @author manuel.mauky
 * 
 */
public class StageFactory {

	private final FxmlFactory fxmlFactory;

	public StageFactory(final FxmlFactory fxmlFactory) {
		this.fxmlFactory = fxmlFactory;
	}

	public Stage createStage(final StringConfig path, final Object controller) {

		Parent fxmlRoot = fxmlFactory.getFxmlRoot(path.get(), controller);

		Stage stage = new Stage();
		stage.setScene(new Scene(fxmlRoot));

		return stage;
	}

}
