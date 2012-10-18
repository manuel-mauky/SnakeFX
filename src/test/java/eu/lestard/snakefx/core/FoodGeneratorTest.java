package eu.lestard.snakefx.core;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import eu.lestard.snakefx.util.Callback;

public class FoodGeneratorTest {

	private FoodGenerator foodGenerator;

	private Grid gridMock;

	private Snake snakeMock;

	@Before
	public void setup() {
		gridMock = mock(Grid.class);
		snakeMock = mock(Snake.class);
	}

	@Test
	public void testGenerateFood() {
		foodGenerator = new FoodGenerator(gridMock, snakeMock);

		verify(snakeMock).addPointsEventListener(any(Callback.class));


		Field field = new Field(0, 0, 10);

		when(gridMock.getRandomEmptyField()).thenReturn(field);

		foodGenerator.generateFood();

		assertThat(field.getState()).isEqualTo(State.FOOD);
	}
}