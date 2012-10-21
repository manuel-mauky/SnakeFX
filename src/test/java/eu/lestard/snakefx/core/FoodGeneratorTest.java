package eu.lestard.snakefx.core;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import org.junit.Before;
import org.junit.Test;

public class FoodGeneratorTest {

	private FoodGenerator foodGenerator;

	private Grid gridMock;
	private IntegerProperty pointsProperty;

	@Before
	public void setup() {
		gridMock = mock(Grid.class);

		pointsProperty = new SimpleIntegerProperty(0);
	}

	@Test
	public void testGenerateFood() {
		foodGenerator = new FoodGenerator(gridMock, pointsProperty);

		Field field = new Field(0, 0, 10);

		when(gridMock.getRandomEmptyField()).thenReturn(field);

		foodGenerator.generateFood();

		assertThat(field.getState()).isEqualTo(State.FOOD);
	}

	@Test
	public void testGenerationWhenPointsAreAddedToProperty() {
		foodGenerator = new FoodGenerator(gridMock, pointsProperty);

		Field field = new Field(0, 0, 10);
		field.changeState(State.EMPTY);
		when(gridMock.getRandomEmptyField()).thenReturn(field);

		pointsProperty.set(1);

		assertThat(field.getState()).isEqualTo(State.FOOD);
	}

	@Test
	public void testNoFoodIsGeneratedWhenPointsPropertyIsResetToZero() {
		pointsProperty.set(10);

		foodGenerator = new FoodGenerator(gridMock, pointsProperty);

		Field field = new Field(0, 0, 10);
		field.changeState(State.EMPTY);
		when(gridMock.getRandomEmptyField()).thenReturn(field);

		pointsProperty.set(0);

		assertThat(field.getState()).isEqualTo(State.EMPTY);
	}
}