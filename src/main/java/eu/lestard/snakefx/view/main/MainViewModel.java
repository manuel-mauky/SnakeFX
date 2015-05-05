package eu.lestard.snakefx.view.main;

import de.saxsys.mvvmfx.ViewModel;
import eu.lestard.grid.GridModel;
import eu.lestard.snakefx.core.NewGameFunction;
import eu.lestard.snakefx.core.State;
import eu.lestard.snakefx.viewmodel.CentralViewModel;

import javax.inject.Singleton;

@Singleton
public class MainViewModel implements ViewModel {

    private final GridModel<State> gridModel;

    public MainViewModel(final CentralViewModel centralViewModel, final GridModel<State> gridModel, NewGameFunction newGame) {
        this.gridModel = gridModel;

        gridModel.setDefaultState(State.EMPTY);

        gridModel.numberOfColumns().bind(centralViewModel.gridSize);
        gridModel.numberOfRows().bind(centralViewModel.gridSize);
//
//        gridModel.setNumberOfColumns(Config.ROW_AND_COLUMN_COUNT.get());
//        gridModel.setNumberOfRows(Config.ROW_AND_COLUMN_COUNT.get());

        newGame.run();
    }

    public GridModel<State> getGridModel(){
        return gridModel;
    }

}
