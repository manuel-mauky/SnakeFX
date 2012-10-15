package eu.lestard.snakefx.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class is the grid of the game. It contains a collection of {@link Field}
 * instances.
 *
 * @author manuel.mauky
 *
 */
public class Grid {

	private final Integer rowAndColumnCount;
	private final Integer gridSizeInPixel;

	private final List<Field> fields = new ArrayList<Field>();

	/**
	 * @param rowAndColumnCount
	 *            the number of rows and columns for this grid.
	 * @param gridSizeInPixel
	 *            the size of the grid in pixel
	 */
	public Grid(final int rowAndColumnCount, final int gridSizeInPixel) {
		this.rowAndColumnCount = rowAndColumnCount;
		this.gridSizeInPixel = gridSizeInPixel;
	}

	/**
	 * This method initializes the grid. According to the given parameter
	 * {@link rowAndColumnCount} the fields ({@link Field}) are created with the
	 * coordinates and the size that is calculated with the given parameter
	 * {@link gridSizeInPixel}.
	 *
	 */
	public void init() {

		for (int y = 0; y < rowAndColumnCount; y++) {
			for (int x = 0; x < rowAndColumnCount; x++) {
				Field f = new Field(x, y, (gridSizeInPixel / rowAndColumnCount));
				fields.add(f);
			}
		}

	}

	/**
	 * @return an unmodifiable list of all fields.
	 */
	public List<Field> getFields() {
		return Collections.unmodifiableList(fields);
	}

}
