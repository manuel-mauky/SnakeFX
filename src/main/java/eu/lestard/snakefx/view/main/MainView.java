package eu.lestard.snakefx.view.main;


import de.saxsys.jfx.mvvm.api.FxmlView;
import de.saxsys.jfx.mvvm.api.InjectViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

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
    private Pane gridContainer;

    @InjectViewModel
    private MainViewModel viewModel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewModel.getFields().forEach(field -> {
            gridContainer.getChildren().add(field.getRectangle());
        });
    }


}
