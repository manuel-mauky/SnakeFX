package eu.lestard.snakefx.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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

	/**
	 *
	 * @param x
	 *            the x coordinate
	 * @param y
	 *            the y coordinate
	 * @return the field with the given coordinates or null if no field with
	 *         this coordinates is available.
	 */
	public Field getXY(final int x, final int y) {
		for (Field f : fields) {
			if (f.getX() == x && f.getY() == y) {
				return f;
			}
		}
		return null;
	}

	/**
	* returns the field that is located next to the given field in the given
	* direction.
	*
	* @param field
	* @param direction
	* @return the field in the given direction
	*/
	public Field getFromDirection(final Field field, final Direction direction) {
		int x = field.getX();
		int y = field.getY();

		switch (direction) {
		case DOWN:
			y += 1;
			break;
		case LEFT:
			x -= 1;
			break;
		case RIGHT:
			x += 1;
			break;
		case UP:
			y -= 1;
			break;
		}

		x += rowAndColumnCount;
		y += rowAndColumnCount;
		x = x % rowAndColumnCount;
		y = y % rowAndColumnCount;

		return getXY(x, y);
	}

	public Field getRandomEmptyField() {

		List<Field> temp = new ArrayList<Field>();

		// Get all empty fields
		for (Field f : fields) {
			if (f.getState().equals(State.EMPTY)) {
				temp.add(f);
			}
		}

		if (temp.isEmpty()) {
			return null;
		} else {
			int nextInt = new Random().nextInt(temp.size());
			return temp.get(nextInt);
		}
	}

	public void newGame() {
		for (Field f : fields) {
			f.changeState(State.EMPTY);
		}
	}

}
