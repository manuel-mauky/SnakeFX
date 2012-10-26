package eu.lestard.snakefx.view.controller;

import javafx.animation.Animation.Status;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import eu.lestard.snakefx.core.GameLoop;

public class PlayPauseController {

	private final GameLoop gameLoop;

	private final StringProperty buttonLabel;

	private final BooleanProperty disabled;

	public PlayPauseController(final GameLoop gameLoop,
			final ReadOnlyBooleanProperty collisionProperty) {
		this.gameLoop = gameLoop;

		buttonLabel = new SimpleStringProperty();
		disabled = new SimpleBooleanProperty();


		collisionProperty.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(final ObservableValue<? extends Boolean> arg0,
					final Boolean oldValue, final Boolean newValue) {
				if (newValue) {
					disabled.set(true);
				}
			}
		});
	}

	public StringProperty buttonLabelProperty() {
		return buttonLabel;
	}

	public BooleanProperty disabledProperty() {
		return disabled;
	}

	public void enableButton() {
		disabled.set(false);
		buttonLabel.set("Play");
	}



	public void togglePlayPause() {
		Status status = gameLoop.getStatus();
		switch (status) {
		case PAUSED:
			buttonLabel.set("Pause");
			gameLoop.play();
			break;
		case RUNNING:
			buttonLabel.set("Resume");
			gameLoop.pause();
			break;
		case STOPPED:
			buttonLabel.set("Pause");
			gameLoop.play();
			break;
		}
	}

}
