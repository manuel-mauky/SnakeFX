package eu.lestard.snakefx.core;

import java.util.ArrayList;
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

	public Grid(final int rowAndColumnCount, final int gridSizeInPixel) {
		this.rowAndColumnCount = rowAndColumnCount;
		this.gridSizeInPixel = gridSizeInPixel;
	}

	public void init() {

		for (int y = 0; y < rowAndColumnCount; y++) {
			for (int x = 0; x < rowAndColumnCount; x++) {
				Field f = new Field(x, y, (gridSizeInPixel / rowAndColumnCount));
				fields.add(f);
			}
		}

	}

	public List<Field> getFields() {
		return fields;
	}

}
