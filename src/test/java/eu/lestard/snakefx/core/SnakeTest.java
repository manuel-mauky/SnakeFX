package eu.lestard.snakefx.core;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

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

	}
}