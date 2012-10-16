package eu.lestard.snakefx.view.controller;

import javafx.animation.Animation.Status;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import eu.lestard.snakefx.core.GameLoop;

public class PlayPauseController {

	private GameLoop gameLoop;

	private StringProperty buttonLabel;

	private BooleanProperty disabled;

	public PlayPauseController(GameLoop gameLoop){
		this.gameLoop = gameLoop;

		buttonLabel = new SimpleStringProperty();
		disabled = new SimpleBooleanProperty();
	}

	public StringProperty buttonLabelProperty(){
		return buttonLabel;
	}

	public BooleanProperty disabledProperty(){
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

	public void onCollision() {
		disabled.set(true);
	}

}
