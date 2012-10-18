package eu.lestard.snakefx.core;

import eu.lestard.snakefx.util.Callback;




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

	public FoodGenerator(final Grid grid, final Snake snake) {
		this.grid = grid;
		snake.addPointsEventListener(new Callback(){
			@Override
			public void call() {
				generateFood();
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