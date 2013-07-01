package eu.lestard.snakefx.core;

import static eu.lestard.snakefx.config.IntegerConfig.GRID_SIZE_IN_PIXEL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javafx.collections.ObservableList;

import com.sun.javafx.collections.ObservableListWrapper;

import eu.lestard.snakefx.viewmodel.ViewModel;

/**
 * This class is the grid of the game. It contains a collection of {@link Field}
 * instances.
 * 
 * @author manuel.mauky
 * 
 */
public class Grid {

	private final Integer gridSizeInPixel;

	private final ObservableList<Field> fields = new ObservableListWrapper<>(new ArrayList<Field>());

	private final ViewModel viewModel;

	/**
	 * @param viewModel
	 *            the viewModel instance.
	 */
	public Grid(final ViewModel viewModel) {
		this.viewModel = viewModel;
		gridSizeInPixel = GRID_SIZE_IN_PIXEL.get();
	}

	/**
	 * This method initializes the grid. According to the
	 * {@link ViewModel#gridSizeProperty()} the fields ({@link Field}) are
	 * created with the coordinates and the size that is calculated with the
	 * given parameter {@link gridSizeInPixel}.
	 * 
	 */
	public void init() {
		final int gridSize = viewModel.gridSize.get();
		for (int y = 0; y < gridSize; y++) {
			for (int x = 0; x < gridSize; x++) {
				final Field f = new Field(x, y, (gridSizeInPixel / gridSize));
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
		for (final Field f : fields) {
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

		final int gridSize = viewModel.gridSize.get();

		x += gridSize;
		y += gridSize;
		x = x % gridSize;
		y = y % gridSize;

		return getXY(x, y);
	}

	public Field getRandomEmptyField() {

		final List<Field> temp = new ArrayList<Field>();

		// Get all empty fields
		for (final Field f : fields) {
			if (f.getState().equals(State.EMPTY)) {
				temp.add(f);
			}
		}

		if (temp.isEmpty()) {
			return null;
		} else {
			final int nextInt = new Random().nextInt(temp.size());
			return temp.get(nextInt);
		}
	}

	public void newGame() {
		for (final Field f : fields) {
			f.changeState(State.EMPTY);
		}
	}

}
