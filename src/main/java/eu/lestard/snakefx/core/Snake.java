package eu.lestard.snakefx.core;

import java.util.ArrayList;
import java.util.List;

import eu.lestard.snakefx.viewmodel.ViewModel;

/**
 * This class represents the snake.
 * 
 * @author manuel.mauky
 * 
 */
public class Snake {

	private Field head;

	private final Grid grid;

	private final int x;
	private final int y;

	private Direction currentDirection;

	private Direction nextDirection;

	private final List<Field> tail;


	private final ViewModel viewModel;


	/**
	 * @param grid
	 *            the grid on which the snake is created
	 * @param x
	 *            the x coordinate where the snake is created
	 * @param y
	 *            the y coordinate where the snake is created
	 * @param points
	 *            the property for the points
	 */
	public Snake(ViewModel viewModel, final Grid grid, final int x, final int y) {
		this.viewModel = viewModel;
		this.grid = grid;
		this.x = x;
		this.y = y;

		tail = new ArrayList<Field>();
	}

	/**
	 * Initalizes the fields of the snake.
	 */
	public void init() {
		setHead(grid.getXY(x, y));

		viewModel.collisionProperty().set(false);

		viewModel.pointsProperty().set(0);

		currentDirection = Direction.UP;
		nextDirection = Direction.UP;
	}

	/**
	 * Change the direction of the snake. The direction is only changed when the
	 * new direction has <bold>not</bold> the same orientation as the old one.
	 * 
	 * For example, when the snake currently has the direction UP and the new
	 * direction should be DOWN, nothing will happend because both directions
	 * are vertical.
	 * 
	 * This is to prevent the snake from moving directly into its own tail.
	 * 
	 * @param newDirection
	 */
	public void changeDirection(final Direction newDirection) {
		if (!newDirection.hasSameOrientation(currentDirection)) {
			nextDirection = newDirection;
		}
	}

	/**
	 * Move the snake by one field.
	 */
	public void move() {
		currentDirection = nextDirection;

		Field newHead = grid.getFromDirection(head, currentDirection);

		if (newHead.getState().equals(State.TAIL)) {
			viewModel.collisionProperty().set(true);
			return;
		}

		boolean grow = false;
		if (newHead.getState().equals(State.FOOD)) {
			grow = true;
		}

		Field lastField = head;

		for (int i = 0; i < tail.size(); i++) {
			Field f = tail.get(i);

			lastField.changeState(State.TAIL);
			tail.set(i, lastField);

			lastField = f;
		}

		if (grow) {
			grow(lastField);
			addPoints();
		} else {
			lastField.changeState(State.EMPTY);
		}

		setHead(newHead);
	}

	public void newGame() {
		tail.clear();
		init();
	}

	private void setHead(final Field head) {
		this.head = head;
		head.changeState(State.HEAD);
	}

	/**
	 * The given field is added to the tail of the snake and gets the state
	 * TAIL.
	 * 
	 * @param field
	 */
	private void grow(final Field field) {
		field.changeState(State.TAIL);
		tail.add(field);
	}

	private void addPoints() {
		int current = viewModel.pointsProperty().get();
		viewModel.pointsProperty().set(current + 1);
	}


}