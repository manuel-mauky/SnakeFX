package eu.lestard.snakefx.core;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

/**
* This class is the game loop of the game.
*
* @author manuel.mauky
*
*/
public class GameLoop {

	private static final int ONE_SECOND = 1000;
	private static final int FPS = 10;

	private Timeline timeline;

	private Snake snake;

	public GameLoop(Snake snake){
		this.snake = snake;
	}

	/**
	* Initialize the timeline instance.
	*/
	public void init() {
		timeline = TimelineBuilder.create().cycleCount(Animation.INDEFINITE)
				.keyFrames(buildKeyFrame()).build();
	}

	/**
	* This method creates a {@link KeyFrame} instance according to the
	* configured framerate.
	*
	* The KeyFrame handles the movement of the snake.
	*
	* @return
	*/
	private KeyFrame buildKeyFrame() {

		Duration duration = Duration.millis(ONE_SECOND / FPS);

		KeyFrame frame = new KeyFrame(duration,
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(final ActionEvent arg0) {
						snake.move();
					}
				});

		return frame;
	}

	/**
	* delegate to the play method of the timeline.
	*/
	public void play() {
		timeline.play();
	}

	/**
	* delegate to the pause method of the timeline.
	*/
	public void pause() {
		timeline.pause();
	}

}