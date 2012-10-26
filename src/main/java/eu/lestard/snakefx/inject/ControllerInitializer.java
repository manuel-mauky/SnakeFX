package eu.lestard.snakefx.inject;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import eu.lestard.snakefx.core.GameLoop;
import eu.lestard.snakefx.core.Snake;
import eu.lestard.snakefx.core.SpeedLevel;
import eu.lestard.snakefx.view.controller.HighScoreController;
import eu.lestard.snakefx.view.controller.SpeedChangeController;

/**
 * This class is used for cases where controllers need special creation or
 * initialization logic.
 * 
 * It is extracted from the {@link ControllerInjector} to keep it clean.
 * 
 * @author manuel.mauky
 * 
 */
public class ControllerInitializer {

	private static final String FXML_ID_SPEED_CHOICE_BOX = "#speedChoiceBox";


	public void initHighScoreController(final Snake snake,
			final HighScoreController highScoreController,
			final Stage highScoreStage) {

		ReadOnlyBooleanProperty collisionProperty = snake.collisionProperty();

		collisionProperty.addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(final ObservableValue<? extends Boolean> arg0,
					final Boolean oldValue, final Boolean newValue) {
				if (newValue) {
					highScoreStage.show();
					highScoreController.gameFinished();
				}
			}
		});
	}

	public SpeedChangeController createSpeedChangeController(
			final Parent mainRoot, final GameLoop gameLoop) {
		@SuppressWarnings("unchecked")
		final ChoiceBox<SpeedLevel> speedChoiceBox = (ChoiceBox<SpeedLevel>) mainRoot
				.lookup(FXML_ID_SPEED_CHOICE_BOX);

		return new SpeedChangeController(gameLoop, speedChoiceBox);
	}

}
