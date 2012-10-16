package eu.lestard.snakefx.core;

import java.util.ArrayList;
import java.util.List;

import eu.lestard.snakefx.util.Callback;

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

	private List<Callback> pointsListener;

	private List<Callback> collisionListener;

	/**
	 * @param grid
	 *            the grid on which the snake is created
	 * @param x
	 *            the x coordinate where the snake is created
	 * @param y
	 *            the y coordinate where the snake is created
	 */
	public Snake(final Grid grid, final int x, int y) {
		this.grid = grid;
		this.x = x;
		this.y = y;
		tail = new ArrayList<Field>();

		pointsListener = new ArrayList<Callback>();
		collisionListener = new ArrayList<Callback>();
	}

	/**
	 * Initalizes the fields of the snake.
	 */
	public void init() {
		setHead(grid.getXY(x, y));

		currentDirection = Direction.UP;
		nextDirection = Direction.UP;
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
			callCollisionEventListener();
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
			callPointsEventListener();
		} else {
			lastField.changeState(State.EMPTY);
		}

		setHead(newHead);
	}

	public void newGame() {
		tail.clear();
		init();
	}

	public void addPointsEventListener(Callback callback) {
		pointsListener.add(callback);
	}

	private void callPointsEventListener() {
		for (Callback c : pointsListener) {
			c.call();
		}
	}

	public void addCollisionEventListener(Callback callback) {
		collisionListener.add(callback);
	}

	private void callCollisionEventListener() {
		for (Callback c : collisionListener) {
			c.call();
		}
	}

}