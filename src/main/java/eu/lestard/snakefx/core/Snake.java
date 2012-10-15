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
	}

	private void setHead(final Field head) {
		this.head = head;
		head.changeState(State.HEAD);
	}

	public Field getHead() {
		return head;
	}

}