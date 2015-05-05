package eu.lestard.snakefx.view.highscore;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import eu.lestard.snakefx.highscore.HighScoreEntry;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class HighscoreView implements FxmlView<HighscoreViewModel>, Initializable {

    @FXML
    private TableView<HighScoreEntry> tableView;

    @InjectViewModel
    private HighscoreViewModel viewModel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableView.setItems(viewModel.highScoreEntries());

        viewModel.selectedEntry().addListener((observable, oldValue, newValue) -> {
           tableView.getSelectionModel().select(newValue);
        });
    }
}
