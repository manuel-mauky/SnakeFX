package eu.lestard.snakefx.core;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import eu.lestard.snakefx.viewmodel.ViewModel;

public class FoodGeneratorTest {

	private FoodGenerator foodGenerator;

	private Grid gridMock;

	private ViewModel viewModel;

	@Before
	public void setup() {
		gridMock = mock(Grid.class);
		viewModel = new ViewModel();
		foodGenerator = new FoodGenerator(viewModel, gridMock);
	}

	@Test
	public void testGenerateFood() {
		Field field = new Field(0, 0, 10);

		when(gridMock.getRandomEmptyField()).thenReturn(field);

		foodGenerator.generateFood();

		assertThat(field.getState()).isEqualTo(State.FOOD);
	}

	@Test
	public void testGenerationWhenPointsAreAddedToProperty() {
		Field field = new Field(0, 0, 10);
		field.changeState(State.EMPTY);
		when(gridMock.getRandomEmptyField()).thenReturn(field);

		viewModel.pointsProperty().set(1);

		assertThat(field.getState()).isEqualTo(State.FOOD);
	}

	@Test
	public void testNoFoodIsGeneratedWhenPointsPropertyIsResetToZero() {
		Field field = new Field(0, 0, 10);
		field.changeState(State.EMPTY);

		Field secondField = new Field(0, 1, 10);
		secondField.changeState(State.EMPTY);
		when(gridMock.getRandomEmptyField()).thenReturn(field).thenReturn(secondField);


		// first set the points to 10, food is generated for first field.
		viewModel.pointsProperty().set(10);


		// now set the points back to 0. now no food must be generated for
		// second field.
		viewModel.pointsProperty().set(0);

		assertThat(secondField.getState()).isEqualTo(State.EMPTY);
	}
}