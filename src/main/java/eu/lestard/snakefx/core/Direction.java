package eu.lestard.snakefx.core;

/**
* This enum represents the directions that the snake can go.
*
* @author manuel.mauky
*
*/
public enum Direction {

	UP(false),

	DOWN(false),

	LEFT(true),

	RIGHT(true);

	private boolean horizontal;

	private Direction(final boolean horizontal) {
		this.horizontal = horizontal;
	}

	public boolean hasSameOrientation(final Direction other) {
		if (other == null) {
			return false;
		}
		return (horizontal == other.horizontal);
	}
}
