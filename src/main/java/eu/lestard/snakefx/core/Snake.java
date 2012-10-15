package eu.lestard.snakefx.core;

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

	private Direction direction;

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
	}

	/**
	 * Initalizes the fields of the snake.
	 */
	public void init() {
		setHead(grid.getXY(x, y));

		direction = Direction.UP;
	}

	private void setHead(final Field head) {
		this.head = head;
		head.changeState(State.HEAD);
	}

	public Field getHead() {
		return head;
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
		if (!newDirection.hasSameOrientation(direction)) {
			direction = newDirection;
		}
	}

	/**
	* Move the snake by one field.
	*/
	public void move() {
		Field newHead = grid.getFromDirection(head, direction);

		head.changeState(State.EMPTY);

		setHead(newHead);
	}

}