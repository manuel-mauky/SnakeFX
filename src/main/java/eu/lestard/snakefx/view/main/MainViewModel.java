package eu.lestard.snakefx.view.main;

import de.saxsys.jfx.mvvm.api.ViewModel;
import eu.lestard.grid.GridModel;
import eu.lestard.snakefx.config.Config;
import eu.lestard.snakefx.core.NewGameFunction;
import eu.lestard.snakefx.core.State;

public class MainViewModel implements ViewModel {

    private final GridModel<State> gridModel;

    public MainViewModel( final GridModel<State> gridModel, NewGameFunction newGame) {
        this.gridModel = gridModel;

        gridModel.setDefaultState(State.EMPTY);
        gridModel.setNumberOfColumns(Config.ROW_AND_COLUMN_COUNT.get());
        gridModel.setNumberOfRows(Config.ROW_AND_COLUMN_COUNT.get());

        newGame.accept(null);
    }

    public GridModel<State> getGridModel(){
        return gridModel;
    }

}
