package eu.lestard.snakefx.view.controller;

import javafx.animation.Animation.Status;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import eu.lestard.snakefx.viewmodel.ViewModel;

public class PlayPauseController {

	private final StringProperty buttonLabel = new SimpleStringProperty();

	private final BooleanProperty disabled = new SimpleBooleanProperty();

	private final ViewModel viewModel;

	public PlayPauseController(ViewModel viewModel) {
		this.viewModel = viewModel;
		viewModel.collisionProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(final ObservableValue<? extends Boolean> arg0, final Boolean oldValue,
					final Boolean newValue) {
				if (newValue) {
					disabled.set(true);
				}
			}
		});
	}

	// TODO needed?
	public StringProperty buttonLabelProperty() {
		return buttonLabel;
	}

	// TODO needed?
	public BooleanProperty disabledProperty() {
		return disabled;
	}

	public void enableButton() {
		disabled.set(false);
		buttonLabel.set("Play");
	}

	public void togglePlayPause() {
		switch (viewModel.gameloopStatusProperty().get()) {
		case PAUSED:
			buttonLabel.set("Pause");
			viewModel.gameloopStatusProperty().set(Status.RUNNING);
			break;
		case RUNNING:
			buttonLabel.set("Resume");
			viewModel.gameloopStatusProperty().set(Status.PAUSED);
			break;
		case STOPPED:
			buttonLabel.set("Pause");
			viewModel.gameloopStatusProperty().set(Status.RUNNING);
			break;
		}
	}

}
