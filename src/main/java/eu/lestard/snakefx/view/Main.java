package eu.lestard.snakefx.view;


import de.saxsys.jfx.mvvm.base.view.View;
import eu.lestard.snakefx.view.viewmodels.MainViewModel;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Presenter class for the Main.fxml file.
 *
 * @author manuel.mauky
 *
 */
public class Main extends View<MainViewModel>{

    @FXML
    private Pane gridContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getViewModel().getFields().forEach(field -> {
            gridContainer.getChildren().add(field.getRectangle());
        });
    }


}
