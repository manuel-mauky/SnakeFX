package eu.lestard.snakefx.config;

/**
 * This enum represents configuration parameters of type integer.
 *
 * Configuration parameters are key value pairs and this enum represents the
 * key's.
 *
 * Every key has a default value that is used by the application when no other
 * value is provided.
 *
 * @author manuel.mauky
 *
 */
public enum IntegerKey {

	/**
	 * The default value that is used by the injection mechanism when no other
	 * key is specified.
	 */
	DEFAULT(0),

	/**
	 * The number of rows and columns of the grid. In this game the grid is a
	 * square and so the number of rows and columns are equal.
	 */
	ROW_AND_COLUMN_COUNT(15),

	/**
	 * The size of the grid in pixel.
	 */
	GRID_SIZE_IN_PIXEL(500)

	;

	private Integer defaultValue;

	private IntegerKey(final Integer defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * @return the specified default value of the key.
	 */
	public Integer getDefaultValue() {
		return defaultValue;
	}
}