package eu.lestard.snakefx.core;

import com.sun.javafx.collections.ObservableListWrapper;
import eu.lestard.snakefx.viewmodel.ViewModel;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static eu.lestard.snakefx.config.Config.*;

/**
 * This class is the grid of the game. It contains a collection of {@link Field}
 * instances.
 * 
 * @author manuel.mauky
 * 
 */
public class Grid {

	private final Integer gridSizeInPixel;

	private final ObservableList<Field> fields = new ObservableListWrapper<>(new ArrayList<>());

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
	 * {@link ViewModel#gridSize} the fields ({@link Field}) are
	 * created with the coordinates and the size that is calculated with the
	 * value of {@link eu.lestard.snakefx.config.Config#GRID_SIZE_IN_PIXEL}.
	 * 
	 */
	public void init() {
		final int gridSize = viewModel.gridSize.get();

		for (int y = 0; y < gridSize; y++) {
			for (int x = 0; x < gridSize; x++) {
                fields.add(new Field(x, y, (gridSizeInPixel / gridSize)));
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
        return fields.stream()
                .filter(field -> (field.getX() == x && field.getY() == y))
                .findFirst()
                .orElse(null);
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
        // filter the list to only empty fields.
        List<Field> emptyFields = fields.stream()
                .filter(field -> field.getState().equals(State.EMPTY))
                .collect(Collectors.<Field>toList());

		if (emptyFields.isEmpty()) {
			return null;
		} else {
			final int nextInt = new Random().nextInt(emptyFields.size());
			return emptyFields.get(nextInt);
		}
	}

	public void newGame() {
        fields.forEach(field -> {
            field.changeState(State.EMPTY);
        });
	}
}
