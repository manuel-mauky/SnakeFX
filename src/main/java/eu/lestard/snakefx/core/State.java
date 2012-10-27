package eu.lestard.snakefx.core;

import javafx.scene.paint.Color;

/**
 * Represents the states that a field can has.
 * 
 * Every state is connected to a {@link Color} that the field will get when it
 * is in the given state.
 * 
 * @author manuel.mauky
 * 
 */
public enum State {

	EMPTY(Color.WHITE),

	HEAD(Color.DARKGREEN),

	TAIL(Color.FORESTGREEN),

	FOOD(Color.BLACK);

	private Color color;

	private State(final Color color) {
		this.color = color;
	}

	/**
	 * @return the color of the State.
	 */
	public Color getColor() {
		return color;
	}

}