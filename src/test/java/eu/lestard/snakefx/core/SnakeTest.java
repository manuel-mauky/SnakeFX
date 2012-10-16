package eu.lestard.snakefx.core;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

import eu.lestard.snakefx.util.Callback;

public class SnakeTest {
	private Snake snake;

	private Grid gridMock;

	private static final int X = 4;
	private static final int Y = 2;

	private Callback pointsEventCallback;

	@Before
	public void setUp() {
		gridMock = mock(Grid.class);

		pointsEventCallback = mock(Callback.class);

		snake = new Snake(gridMock, X, Y);

		snake.addPointsEventListener(pointsEventCallback);
	}

	@Test
	public void testInitialization() {
		Field field = mock(Field.class);

		when(gridMock.getXY(X, Y)).thenReturn(field);

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
		when(oldHead.getState()).thenReturn(State.EMPTY);
		when(gridMock.getXY(X, Y)).thenReturn(oldHead);

		snake.init();

		Field newHead = mock(Field.class);
		when(newHead.getState()).thenReturn(State.EMPTY);
		when(gridMock.getFromDirection(oldHead, Direction.UP)).thenReturn(
				newHead);

		snake.move();

		assertThat(snake.getHead()).isEqualTo(newHead);

		verify(oldHead).changeState(State.EMPTY);
	}

	/**
	* When the snake moves to a field that has the state "FOOD" the snake
	* should grow by 1 field.
	*/
	@Test
	public void testGrow() {
		Field field1 = new Field(0, 3, 10);
		// at the start field1 is the head
		when(gridMock.getXY(X, Y)).thenReturn(field1);

		// field2 is above field1
		Field field2 = new Field(0, 2, 10);
		field2.changeState(State.FOOD);
		when(gridMock.getFromDirection(field1, Direction.UP))
				.thenReturn(field2);

		// field3 is above field2
		Field field3 = new Field(0, 1, 10);
		when(gridMock.getFromDirection(field2, Direction.UP))
				.thenReturn(field3);

		snake.init();

		snake.move();

		// the head of the snake is now on field2
		assertThat(snake.getHead()).isEqualTo(field2);

		// field1 is now a part of the tail
		assertThat(field1.getState()).isEqualTo(State.TAIL);

		// The pointsEvent has to be fired
		verify(pointsEventCallback, times(1)).call();

		// Now the snake is moving another field forward. This time the new
		// field (field3)
		// is empty.

		snake.move();

		// field3 becomes the new head
		assertThat(snake.getHead()).isEqualTo(field3);

		// field2 becomes the tail
		assertThat(field2.getState()).isEqualTo(State.TAIL);

		// field1 is now empty
		assertThat(field1.getState()).isEqualTo(State.EMPTY);

	}

	private Direction getDirectionFromSnake() {
		return (Direction) Whitebox.getInternalState(snake, "direction");
	}
}