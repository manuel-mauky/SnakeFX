package eu.lestard.snakefx.core;

import eu.lestard.snakefx.viewmodel.CentralViewModel;


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

	public FoodGenerator(final CentralViewModel viewModel, final Grid grid) {
		this.grid = grid;

        viewModel.points.addListener((observable, oldValue, newValue) -> {
            if (oldValue.intValue() < newValue.intValue()) {
                generateFood();
            }
        });
	}

	/**
	 * Generates new food.
	 */
	public void generateFood() {
		final Field field = grid.getRandomEmptyField();

		field.changeState(State.FOOD);
	}
}