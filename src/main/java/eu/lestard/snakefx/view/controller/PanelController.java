package eu.lestard.snakefx.view.controller;

import javafx.animation.Animation.Status;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import eu.lestard.snakefx.core.SpeedLevel;
import eu.lestard.snakefx.view.FXMLFile;
import eu.lestard.snakefx.viewmodel.ViewModel;

/**
 * UI-Controller class for the fxml file {@link FXMLFile#PANEL}. This controller
 * handles the actions of the side panel.
 * 
 * @author manuel.mauky
 * 
 */
public class PanelController {

	@FXML
	private Label points;

	@FXML
	private ChoiceBox<SpeedLevel> speed;

	@FXML
	private Button playPause;

	private final ViewModel viewModel;

	public PanelController(final ViewModel viewModel) {
		this.viewModel = viewModel;
	}

	@FXML
	public void initialize() {
		points.textProperty().bind(viewModel.pointsProperty().asString());
		speed.getSelectionModel().selectFirst();

		speed.valueProperty().bindBidirectional(viewModel.speedProperty());

		playPause.disableProperty().bind(viewModel.collisionProperty());
	}

	public void togglePlayPause() {
		switch (viewModel.gameloopStatusProperty().get()) {
		case PAUSED:
			playPause.textProperty().set("Pause");
			viewModel.gameloopStatusProperty().set(Status.RUNNING);
			break;
		case RUNNING:
			playPause.textProperty().set("Resume");
			viewModel.gameloopStatusProperty().set(Status.PAUSED);
			break;
		case STOPPED:
			playPause.textProperty().set("Pause");
			viewModel.gameloopStatusProperty().set(Status.RUNNING);
			break;
		}
	}
}
