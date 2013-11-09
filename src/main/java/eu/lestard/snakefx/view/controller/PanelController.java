package eu.lestard.snakefx.view.controller;

import javafx.animation.Animation.Status;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

	private static final String LABEL_START = "Start";
	private static final String LABEL_RESUME = "Resume";
	private static final String LABEL_PAUSE = "Pause";

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
		speed.itemsProperty().get().addAll(SpeedLevel.values());

		points.textProperty().bind(viewModel.points.asString());
		speed.getSelectionModel().selectFirst();

		speed.valueProperty().bindBidirectional(viewModel.speed);

		playPause.disableProperty().bind(viewModel.collision);

		viewModel.gameloopStatus.addListener(new ChangeListener<Status>() {
			@Override
			public void changed(final ObservableValue<? extends Status> arg0, final Status oldStatus,
					final Status newStatus) {
				if (Status.STOPPED.equals(newStatus)) {
					playPause.textProperty().set(LABEL_START);
				}
			}
		});
	}

	@FXML
	public void togglePlayPause() {
		final Status status = viewModel.gameloopStatus.get();
		switch (status) {
		case PAUSED:
			playPause.textProperty().set(LABEL_PAUSE);
			viewModel.gameloopStatus.set(Status.RUNNING);
			break;
		case RUNNING:
			playPause.textProperty().set(LABEL_RESUME);
			viewModel.gameloopStatus.set(Status.PAUSED);
			break;
		case STOPPED:
			playPause.textProperty().set(LABEL_PAUSE);
			viewModel.gameloopStatus.set(Status.RUNNING);
			break;
		}
	}
}
