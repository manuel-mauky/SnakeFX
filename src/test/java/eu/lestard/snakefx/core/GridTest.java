package eu.lestard.snakefx.core;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

public class GridTest {

	private Grid grid;

	/**
	 * The init method has to create a list of {@link Field} instances with the
	 * right coordinates.
	 */
	@Test
	public void testInitialization() {
		int rowAndColumnCount = 4;
		int gridSizeInPixel = 300;

		grid = new Grid(rowAndColumnCount, gridSizeInPixel);
		grid.init();

		List<Field> fields = grid.getFields();

		int fieldCount = rowAndColumnCount * rowAndColumnCount;
		assertThat(fields).hasSize(fieldCount);

		// first check the calculated Size for all Fields

		for (Field f : fields) {
			assertThat(f.getRectangle().getWidth()).isEqualTo(
					gridSizeInPixel / rowAndColumnCount);
			assertThat(f.getRectangle().getHeight()).isEqualTo(
					gridSizeInPixel / rowAndColumnCount);
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

		int rowAndColumnCount = 4;
		int gridSizeInPixel = 300;

		grid = new Grid(rowAndColumnCount, gridSizeInPixel);
		grid.init();

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
		int rowAndColumnCount = 4;
		int gridSizeInPixel = 300;

		grid = new Grid(rowAndColumnCount, gridSizeInPixel);
		grid.init();

		Field x2y5 = grid.getXY(2, 5);

		assertThat(x2y5).isNull();

	}
}
