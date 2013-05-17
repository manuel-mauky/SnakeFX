package eu.lestard.snakefx.core;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import eu.lestard.snakefx.viewmodel.ViewModel;


/**
 * This class generates new food that the snake can eat.
 * 
 * "Food" means that the given field gets the state {@link State#FOOD}.
 * 
 * The food is generated at an empty field at a random location.
 * 
 * @author manuel.mauky
 * 
 */
public class FoodGenerator {

	private final Grid grid;

	public FoodGenerator(final ViewModel viewModel, final Grid grid) {
		this.grid = grid;

		viewModel.pointsProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(final ObservableValue<? extends Number> arg0, final Number oldValue,
					final Number newValue) {
				if (oldValue.intValue() < newValue.intValue()) {
					generateFood();
				}
			}
		});
	}

	/**
	 * Generates new food.
	 */
	public void generateFood() {
		Field field = grid.getRandomEmptyField();

		field.changeState(State.FOOD);
	}
}