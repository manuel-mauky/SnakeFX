package eu.lestard.snakefx.view.highscore;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class NewHighscoreView implements FxmlView<NewHighscoreViewModel>, Initializable {

    @FXML
    private TextField playername;

    @FXML
    private Label errorMessage;

    @FXML
    private Label points;

    @InjectViewModel
    private NewHighscoreViewModel viewModel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        points.textProperty().bind(viewModel.pointsLabelText());

        errorMessage.visibleProperty().bind(viewModel.errorMessageVisible());
    }

    @FXML
    public void addEntry(){
        viewModel.addEntry(playername.getText());
    }
}
