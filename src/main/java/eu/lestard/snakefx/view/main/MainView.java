package eu.lestard.snakefx.view.main;


import de.saxsys.jfx.mvvm.api.FxmlView;
import de.saxsys.jfx.mvvm.api.InjectViewModel;
import eu.lestard.grid.GridView;
import eu.lestard.snakefx.core.State;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Presenter class for the MainView.fxml file.
 *
 * @author manuel.mauky
 *
 */
public class MainView implements FxmlView<MainViewModel>, Initializable {

    @FXML
    private BorderPane mainContainer;

    @InjectViewModel
    private MainViewModel viewModel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GridView<State> gridView = new GridView<>();

        gridView.addColorMapping(State.EMPTY, Color.WHITE);
        gridView.addColorMapping(State.HEAD, Color.DARKGREEN);
        gridView.addColorMapping(State.TAIL, Color.FORESTGREEN);
        gridView.addColorMapping(State.FOOD, Color.BLACK);

        gridView.strokeWidthProperty().set(0.5);


        gridView.setGridModel(viewModel.getGridModel());

        mainContainer.setCenter(gridView);
    }


}
