package eu.lestard.snakefx.view.presenter;

import javafx.application.Platform;
import javafx.fxml.FXML;
import eu.lestard.snakefx.util.Function;
import eu.lestard.snakefx.view.FXMLFile;
import eu.lestard.snakefx.viewmodel.ViewModel;

/**
 * UI-Controller for the fxml file {@link FXMLFile#MENU}. This presenter
 * handles the menu actions.
 * 
 * @author manuel.mauky
 * 
 */
public class MenuPresenter {

	private final ViewModel viewModel;
	private final Function newGameFunction;

	public MenuPresenter(final ViewModel viewModel, final Function newGameFunction) {
		this.viewModel = viewModel;
		this.newGameFunction = newGameFunction;
	}

	@FXML
	public void newGame() {
		newGameFunction.call();
	}

	@FXML
	public void showHighscores() {
		viewModel.highscoreWindowOpen.set(true);
	}

	@FXML
	public void about() {
		viewModel.aboutWindowOpen.set(true);
	}

	@FXML
	public void exit() {
		Platform.exit();
	}

}
