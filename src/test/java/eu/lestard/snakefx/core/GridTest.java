package eu.lestard.snakefx.core;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class GridTest {

	private static final int ROW_AND_COLUMN_COUNT = 4;
	private static final int GRID_SIZE_IN_PIXEL = 300;

	private Grid grid;

	@Before
	public void setUp() {
		grid = new Grid(ROW_AND_COLUMN_COUNT, GRID_SIZE_IN_PIXEL);
		grid.init();
	}

	/**
	* The init method has to create a list of {@link Field} instances with the
	* right coordinates.
	*/
	@Test
	public void testInitialization() {

		List<Field> fields = grid.getFields();

		int fieldCount = ROW_AND_COLUMN_COUNT * ROW_AND_COLUMN_COUNT;
		assertThat(fields).hasSize(fieldCount);

		// first check the calculated Size for all Fields

		for (Field f : fields) {
			assertThat(f.getRectangle().getWidth()).isEqualTo(
					GRID_SIZE_IN_PIXEL / ROW_AND_COLUMN_COUNT);
			assertThat(f.getRectangle().getHeight()).isEqualTo(
					GRID_SIZE_IN_PIXEL / ROW_AND_COLUMN_COUNT);
		}

		// choose some sample fields and check there x and y values
		/*
		 * o|_|_|_
		 * _|_|_|_
		 * _|_|_|_
		 * | | |
		 */
		Field x0y0 = fields.get(0);
		assertThat(x0y0.getX()).isEqualTo(0);
		assertThat(x0y0.getY()).isEqualTo(0);

		/*
		 * _|_|_|_
		 * _|_|_|_
		 * _|o|_|_
		 * | | |
		 */
		Field x1y2 = fields.get(9);
		assertThat(x1y2.getX()).isEqualTo(1);
		assertThat(x1y2.getY()).isEqualTo(2);

		/*
		 * _|_|_|_
		 * _|_|_|_
		 * _|_|_|_
		 * | | |o
		 */
		Field x3y3 = fields.get(fieldCount - 1);
		assertThat(x3y3.getX()).isEqualTo(3);
		assertThat(x3y3.getY()).isEqualTo(3);
	}

	@Test
	public void testGetXY() {
		Field x2y1 = grid.getXY(2, 1);

		assertThat(x2y1.getX()).isEqualTo(2);
		assertThat(x2y1.getY()).isEqualTo(1);
	}

	/**
	* when the value for x or y (or both) is bigger than the grid then null has
	* to be returned.
	*/
	@Test
	public void testGetXYFail() {
		Field x2y5 = grid.getXY(2, 5);

		assertThat(x2y5).isNull();
	}

	/**
	* From a given field get the field next to it from a given direction.
	*/
	@Test
	public void testGetFromDirection() {
		Field x2y2 = grid.getXY(2, 2);

		Field x2y3 = grid.getFromDirection(x2y2, Direction.DOWN);

		assertThat(x2y3.getX()).isEqualTo(2);
		assertThat(x2y3.getY()).isEqualTo(3);

		Field x3y3 = grid.getFromDirection(x2y3, Direction.RIGHT);

		assertThat(x3y3.getX()).isEqualTo(3);
		assertThat(x3y3.getY()).isEqualTo(3);
	}

	/**
	* In the game when the snake moves outside of the grid on one side it
	* appears again on the other side.
	*
	* When a field is located directly at the border of the grid and the
	* getFromDirection method is called with the direction to the outside of
	* the grid, the field on the other side of the grid on the same row/column
	* has to be returned.
	*/
	@Test
	public void testGetFromDirectionOtherSideOfTheGrid() {

		Field x0y3 = grid.getXY(0, 3);
		Field x3y3 = grid.getFromDirection(x0y3, Direction.LEFT);

		assertThat(x3y3.getX()).isEqualTo(3);
		assertThat(x3y3.getY()).isEqualTo(3);

		x0y3 = grid.getFromDirection(x3y3, Direction.RIGHT);
		assertThat(x0y3.getX()).isEqualTo(0);
		assertThat(x0y3.getY()).isEqualTo(3);

		Field x2y0 = grid.getXY(2, 0);
		Field x2y3 = grid.getFromDirection(x2y0, Direction.UP);

		assertThat(x2y3.getX()).isEqualTo(2);
		assertThat(x2y3.getY()).isEqualTo(3);

		x2y0 = grid.getFromDirection(x2y3, Direction.DOWN);
		assertThat(x2y0.getX()).isEqualTo(2);
		assertThat(x2y0.getY()).isEqualTo(0);
	}

	/**
	* When the newGame method is called, all fields must be reset to status
	* EMPTY.
	*/
	@Test
	public void testNewGame() {

		// First change the state of some fields

		Field x2y1 = grid.getXY(2, 1);
		x2y1.changeState(State.FOOD);

		Field x3y3 = grid.getXY(3, 3);

		x3y3.changeState(State.HEAD);

		Field x0y2 = grid.getXY(0, 2);
		x0y2.changeState(State.TAIL);

		grid.newGame();

		// now the fields must be EMPTY
		assertThat(x2y1.getState()).isEqualTo(State.EMPTY);
		assertThat(x3y3.getState()).isEqualTo(State.EMPTY);
		assertThat(x0y2.getState()).isEqualTo(State.EMPTY);

		// All other fields must be empty too
		List<Field> fields = grid.getFields();
		for (Field field : fields) {
			assertThat(field.getState()).isEqualTo(State.EMPTY);
		}

	}
}
