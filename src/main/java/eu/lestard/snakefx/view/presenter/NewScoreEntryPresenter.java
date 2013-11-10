package eu.lestard.snakefx.view.presenter;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import eu.lestard.snakefx.highscore.HighscoreManager;

public class NewScoreEntryPresenter {

	@FXML
	private TextField playername;

	@FXML
	private Label errorMessage;

	@FXML
	private Label points;

	private final HighscoreManager highScoreManager;

	private final IntegerProperty pointsProperty = new SimpleIntegerProperty();

	public NewScoreEntryPresenter(final HighscoreManager highScoreManager) {
		this.highScoreManager = highScoreManager;
	}

	public IntegerProperty pointsProperty() {
		return pointsProperty;
	}

	@FXML
	public void initialize() {
		points.textProperty().bind(pointsProperty.asString());
	}

	@FXML
	public void addEntry() {
		String name = playername.getText();
		if (!isNameValid(name)) {
			errorMessage.setVisible(true);
			return;
		} else {
			errorMessage.setVisible(false);
		}

		highScoreManager.addScore(name, pointsProperty.get());
		playername.getScene().getWindow().hide();
	}

	private boolean isNameValid(final String name) {
		if (name == null) {
			return false;
		}
		if (name.isEmpty()) {
			return false;
		}
		if (name.contains(",")) {
			return false;
		}

		if (name.contains(";")) {
			return false;
		}

		return true;
	}
}
