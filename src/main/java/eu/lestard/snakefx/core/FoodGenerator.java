package eu.lestard.snakefx.core;

import eu.lestard.grid.Cell;
import eu.lestard.grid.GridModel;
import eu.lestard.snakefx.viewmodel.CentralViewModel;

import java.util.List;
import java.util.Random;


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

    private final GridModel<State> gridModel;

    public FoodGenerator(final CentralViewModel viewModel, final GridModel<State> gridModel) {
        this.gridModel = gridModel;

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
      final List<Cell<State>> emptyCells = gridModel.getCellsWithState(State.EMPTY);

      if(!emptyCells.isEmpty()){
          final int nextInt = new Random().nextInt(emptyCells.size());
          Cell<State> nextFoodCell = emptyCells.get(nextInt);

          nextFoodCell.changeState(State.FOOD);
      }
	}
}