package eu.lestard.snakefx.core;

import eu.lestard.grid.GridModel;
import eu.lestard.snakefx.viewmodel.CentralViewModel;
import javafx.animation.Animation.Status;

import javax.inject.Singleton;
import java.util.function.Consumer;

/**
 * The purpose of this function is to start a new Game.
 *
 * @author manuel.mauky
 */
@Singleton
public class NewGameFunction implements Consumer<Void> {
    private final CentralViewModel viewModel;
    private final GridModel<State> gridModel;
    private final Snake snake;
    private final FoodGenerator foodGenerator;

    public NewGameFunction(final CentralViewModel viewModel, final GridModel<State> gridModel, final Snake snake,
        final FoodGenerator foodGenerator) {
        this.viewModel = viewModel;
        this.gridModel = gridModel;
        this.snake = snake;
        this.foodGenerator = foodGenerator;
    }

    @Override
    public void accept(Void aVoid) {
        viewModel.gameloopStatus.set(Status.STOPPED);

        gridModel.getCells().forEach(cell -> cell.changeState(State.EMPTY));

        snake.newGame();

        foodGenerator.generateFood();

        viewModel.gameloopStatus.set(Status.RUNNING);
        viewModel.gameloopStatus.set(Status.PAUSED);
    }
}
