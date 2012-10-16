package eu.lestard.snakefx.view.controller;

import javafx.animation.Animation.Status;
import javafx.scene.control.Button;
import eu.lestard.snakefx.core.GameLoop;

public class PlayPauseController {

	private GameLoop gameLoop;

	private Button button;

	public PlayPauseController(GameLoop gameLoop){
		this.gameLoop = gameLoop;
	}

	public void init(final Button playPauseButton) {
		button = playPauseButton;
	}

	public void enableButton() {
		button.setDisable(false);
		button.setText("Play");
	}

	public void togglePlayPause() {
		Status status = gameLoop.getStatus();
		switch (status) {
		case PAUSED:
			button.setText("Pause");
			gameLoop.play();
			break;
		case RUNNING:
			button.setText("Resume");
			gameLoop.pause();
			break;
		case STOPPED:
			button.setText("Pause");
			gameLoop.play();
			break;
		}
	}

	public void onCollision() {
		button.setDisable(true);
	}

}
