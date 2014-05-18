package eu.lestard.snakefx.view.menu;

import de.saxsys.jfx.mvvm.api.FxmlView;
import de.saxsys.jfx.mvvm.api.InjectViewModel;
import eu.lestard.snakefx.util.TriggerablePopup;
import eu.lestard.snakefx.view.about.AboutView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuView implements FxmlView<MenuViewModel>, Initializable {

    @InjectViewModel
    private MenuViewModel viewModel;

    @FXML
    private AnchorPane menuViewPane;

    private TriggerablePopup aboutPopup;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menuViewPane.sceneProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                final Window window = menuViewPane.getScene().getWindow();
                aboutPopup = new TriggerablePopup(AboutView.class, (Stage) window);
                aboutPopup.trigger().bindBidirectional(viewModel.aboutPopupVisible());
            }
        });
    }

    @FXML
    public void newGame() {
        viewModel.newGame();
    }

    @FXML
    public void showHighscores() {
        viewModel.showHighscores();
    }

    @FXML
    public void about() {
        viewModel.about();
    }

    @FXML
    public void exit() {
        viewModel.exit();
    }

}
