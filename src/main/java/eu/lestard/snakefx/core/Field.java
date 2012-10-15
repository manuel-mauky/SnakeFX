package eu.lestard.snakefx.core;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This class represents a single field in the {@link Grid} of the game.
 *
 * @author manuel.mauky
 *
 */
public class Field {

	private final Rectangle rectangle;

	private final int x;
	private final int y;

	private State state;

	/**
	 * Creates a new Field with the given sizeInPixel at the location specified
	 * by x and y coordinate.
	 *
	 * x and y are coordinates in the coordinate system of the game {@link Grid}
	 * . They are <bold>not</bold> representing the coordinates in pixel of the
	 * underlying rectangle in the JavaFX canvas.
	 *
	 * The pixel-x and pixel-y coordinates of the underlying rectangle is
	 * calculated from the given x and y coordinate and the sizeInPixel.
	 *
	 * @param x
	 * @param y
	 * @param sizeInPixel
	 */
	public Field(final int x, final int y, final int sizeInPixel) {
		this.x = x;
		this.y = y;

		state = State.EMPTY;

		rectangle = new Rectangle(x * sizeInPixel, y * sizeInPixel,
				sizeInPixel, sizeInPixel);

		rectangle.setStroke(Color.LIGHTGRAY);
		rectangle.setFill(Color.WHITE);

	}

	/**
	 * @return the current state of the field.
	 */
	public State getState() {
		return state;
	}

	/**
	 * The given state is set as the state of the field and the fill color of
	 * the rectangle of this field is changed to the color of the given state.
	 *
	 * @param newState
	 */
	public void changeState(final State newState) {
		state = newState;

		rectangle.setFill(newState.getColor());
	}

	/**
	 * @return the underlying JavaFX {@link Rectangle}.
	 */
	public Rectangle getRectangle() {
		return rectangle;
	}

	/**
	 * The x coordinate of the field in the game's grid.
	 *
	 * @return the x coordinate.
	 */
	public int getX() {
		return x;
	}

	/**
	 * The y coordinate of the field in the game's grid.
	 *
	 * @return y coordinate.
	 */
	public int getY() {
		return y;
	}
}
