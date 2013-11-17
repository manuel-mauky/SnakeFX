package eu.lestard.snakefx.view.presenter;

import eu.lestard.snakefx.core.SpeedLevel;
import eu.lestard.snakefx.view.FXMLFile;
import eu.lestard.snakefx.viewmodel.ViewModel;
import javafx.animation.Animation.Status;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

/**
 * UI-Controller class for the fxml file {@link FXMLFile#PANEL}. This presenter
 * handles the actions of the side panel.
 * 
 * @author manuel.mauky
 * 
 */
public class PanelPresenter {

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

	public PanelPresenter(final ViewModel viewModel) {
		this.viewModel = viewModel;
	}

	@FXML
	public void initialize() {
		speed.itemsProperty().get().addAll(SpeedLevel.values());

		points.textProperty().bind(viewModel.points.asString());
		speed.getSelectionModel().selectFirst();

		speed.valueProperty().bindBidirectional(viewModel.speed);

		playPause.disableProperty().bind(viewModel.collision);

		viewModel.gameloopStatus.addListener((observable, oldStatus, newStatus) -> {
            if (Status.STOPPED.equals(newStatus)) {
                playPause.textProperty().set(LABEL_START);
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
