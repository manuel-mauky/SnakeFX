package eu.lestard.snakefx.config;

/**
 * This enum represents configuration parameters of type integer.
 * 
 * @author manuel.mauky
 */
public enum IntegerConfig {

	/**
	 * The default value that is used by the injection mechanism when no other
	 * key is specified.
	 */
	DEFAULT(0),

	/**
	 * The number of rows and columns of the grid. In this game the grid is a
	 * square and so the number of rows and columns are equal.
	 */
	ROW_AND_COLUMN_COUNT(20),

	/**
	 * The size of the grid in pixel.
	 */
	GRID_SIZE_IN_PIXEL(500),

	/**
	 * The x coordinate of the starting point of the snake
	 */
	SNAKE_START_X(10),

	/**
	 * The y coordinate of the starting point of the snake
	 */
	SNAKE_START_Y(10),

	/**
	 * The max number of HighScore entries that are saved and persisted
	 */
	MAX_SCORE_COUNT(10)

	;

	private Integer value;

	private IntegerConfig(final Integer value) {
		this.value = value;
	}

	/**
	 * @return the specified default value of the key.
	 */
	public Integer get() {
		return value;
	}
}