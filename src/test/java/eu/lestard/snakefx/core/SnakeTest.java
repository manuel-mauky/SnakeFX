package eu.lestard.snakefx.core;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

public class SnakeTest {
	private Snake snake;

	private Grid gridMock;

	private static final int X = 4;
	private static final int Y = 2;

	@Before
	public void setUp() {
		gridMock = mock(Grid.class);
	}

	@Test
	public void testInitialization() {
		Field field = mock(Field.class);

		when(gridMock.getXY(X, Y)).thenReturn(field);

		snake = new Snake(gridMock, X, Y);
		snake.init();

		verify(gridMock, times(1)).getXY(X, Y);
		verify(field, times(1)).changeState(State.HEAD);

		assertThat(snake.getHead()).isEqualTo(field);

		// The direction of the snake is UP on start.
		Direction direction = getDirectionFromSnake();
		assertThat(direction).isEqualTo(Direction.UP);
	}

	@Test
	public void testChangeDirection() {
		snake = new Snake(gridMock, X, Y);

		snake.changeDirection(Direction.LEFT);
		Direction direction = getDirectionFromSnake();

		assertThat(direction).isEqualTo(Direction.LEFT);
	}

	/**
	* When the new direction has the same orientation as the old one ( both are
	* horizontal or both are vertical) no change of the direction should be
	* made.
	*
	* Otherwise the head of the snake would move directly into the tail.
	*/
	@Test
	public void testChangeDirectionNewHasSameOrientationAsOld() {

		snake = new Snake(gridMock, X, Y);

		snake.changeDirection(Direction.DOWN);

		assertThat(getDirectionFromSnake()).isEqualTo(Direction.DOWN);

		// Both old and new directions are vertical.
		snake.changeDirection(Direction.UP);
		assertThat(getDirectionFromSnake()).isEqualTo(Direction.DOWN);

		snake.changeDirection(Direction.LEFT);
		assertThat(getDirectionFromSnake()).isEqualTo(Direction.LEFT);

		// this time both old and new directions are horizontal
		snake.changeDirection(Direction.RIGHT);
		assertThat(getDirectionFromSnake()).isEqualTo(Direction.LEFT);
	}

	@Test
	public void testMove() {

		Field oldHead = mock(Field.class);
		when(gridMock.getXY(X, Y)).thenReturn(oldHead);

		snake = new Snake(gridMock, X, Y);
		snake.init();

		Field newHead = mock(Field.class);
		when(gridMock.getFromDirection(oldHead, Direction.UP)).thenReturn(
				newHead);

		snake.move();

		assertThat(snake.getHead()).isEqualTo(newHead);

		verify(oldHead).changeState(State.EMPTY);
	}

	private Direction getDirectionFromSnake() {
		return (Direction) Whitebox.getInternalState(snake, "direction");
	}
}