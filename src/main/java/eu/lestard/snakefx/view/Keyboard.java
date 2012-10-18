package eu.lestard.snakefx.view;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import eu.lestard.snakefx.core.Direction;
import eu.lestard.snakefx.core.Snake;

public class Keyboard implements EventHandler<KeyEvent> {

	private final Snake snake;

	public Keyboard(final Snake snake) {
		this.snake = snake;
	}

	@Override
	public void handle(final KeyEvent event) {
		KeyCode code = event.getCode();

		switch (code) {
		case UP:
			snake.changeDirection(Direction.UP);
			break;
		case DOWN:
			snake.changeDirection(Direction.DOWN);
			break;
		case LEFT:
			snake.changeDirection(Direction.LEFT);
			break;
		case RIGHT:
			snake.changeDirection(Direction.RIGHT);
			break;
		}
	}
}