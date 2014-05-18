package eu.lestard.snakefx.view.main;

import de.saxsys.jfx.mvvm.api.ViewModel;
import eu.lestard.snakefx.core.Field;
import eu.lestard.snakefx.core.Grid;
import eu.lestard.snakefx.viewmodel.CentralViewModel;

import java.util.List;
import java.util.function.Consumer;

public class MainViewModel implements ViewModel {

    private final Grid grid;

    private final CentralViewModel viewModel;

    private final Consumer<?> newGameFunction;


    public MainViewModel(final CentralViewModel viewModel, final Grid grid, final Consumer<?> newGameFunction) {
        this.viewModel = viewModel;
        this.grid = grid;
        this.newGameFunction = newGameFunction;

        grid.init();
        newGameFunction.accept(null);
    }

    public List<Field> getFields(){
        return grid.getFields();
    }

}
