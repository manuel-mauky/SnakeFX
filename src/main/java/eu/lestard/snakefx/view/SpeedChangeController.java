package eu.lestard.snakefx.view;

import javafx.animation.Animation.Status;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ChoiceBox;
import eu.lestard.snakefx.core.GameLoop;
import eu.lestard.snakefx.core.SpeedLevel;

/**
* This is a controller class that handles the JavaFX Choicebox that the player
* can use to change the speed of the game.
*
* @author manuel.mauky
*
*/
public class SpeedChangeController {
	private final GameLoop gameLoop;

	public SpeedChangeController(final GameLoop gameLoop) {
		this.gameLoop = gameLoop;
	}

	/**
	* Initialize the Choicebox with a selection listener that fires a
	* {@link SpeedChangeEvent} with the new fps value.
	*
	* @param choiceBox
	*/
	public void init(final ChoiceBox<SpeedLevel> choiceBox) {

		choiceBox.getSelectionModel().selectFirst();

		choiceBox.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<SpeedLevel>() {

					@Override
					public void changed(
							final ObservableValue<? extends SpeedLevel> arg0,
							final SpeedLevel oldValue, final SpeedLevel newValue) {
						changeSpeed(newValue);
					}
				});
	}

	/**
	* Change the speed of the {@link GameLoop}.
	*
	* To change the speed the GameLoops timeline instance is rebuild.
	*
	* If the GameLoop was running before the speed change it is restarted after
	* that with the new speed.
	*
	* @param newSpeed
	*/
	public void changeSpeed(final SpeedLevel newSpeed) {
		gameLoop.setFps(newSpeed.getFps());

		Status status = gameLoop.getStatus();

		gameLoop.stop();

		gameLoop.init();

		if (status.equals(Status.RUNNING)) {
			gameLoop.play();
		}

	}
}