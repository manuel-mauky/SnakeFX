package eu.lestard.snakefx.view.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ChoiceBox;
import eu.lestard.snakefx.core.SpeedLevel;
import eu.lestard.snakefx.viewmodel.ViewModel;

/**
 * This is a controller class that handles the JavaFX Choicebox that the player
 * can use to change the speed of the game.
 * 
 * @author manuel.mauky
 * 
 */
public class SpeedChangeController {

	private final ChoiceBox<SpeedLevel> choiceBox;

	private final ViewModel viewModel;

	public SpeedChangeController(final ChoiceBox<SpeedLevel> choiceBox, ViewModel viewModel) {
		this.choiceBox = choiceBox;
		this.viewModel = viewModel;
	}

	public void init() {

		choiceBox.getSelectionModel().selectFirst();


		choiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SpeedLevel>() {

			@Override
			public void changed(final ObservableValue<? extends SpeedLevel> arg0, final SpeedLevel oldValue,
					final SpeedLevel newValue) {
				viewModel.speedProperty().set(newValue);
			}
		});
	}
}