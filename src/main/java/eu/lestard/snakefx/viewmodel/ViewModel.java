package eu.lestard.snakefx.viewmodel;

import static eu.lestard.snakefx.config.IntegerConfig.ROW_AND_COLUMN_COUNT;
import javafx.animation.Animation.Status;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import eu.lestard.snakefx.core.SpeedLevel;

public class ViewModel {

	private final IntegerProperty points = new SimpleIntegerProperty(0);

	private final ObjectProperty<SpeedLevel> speed = new SimpleObjectProperty<>(SpeedLevel.MEDIUM);

	private final BooleanProperty collision = new SimpleBooleanProperty(false);

	private final ObjectProperty<Status> gameloopStatus = new SimpleObjectProperty<>(Status.STOPPED);

	private final IntegerProperty gridSize = new SimpleIntegerProperty(ROW_AND_COLUMN_COUNT.get());

	private final BooleanProperty highscoreWindowOpen = new SimpleBooleanProperty(false);

	private final BooleanProperty newHighscoreWindowOpen = new SimpleBooleanProperty(false);

	private final BooleanProperty aboutWindowOpen = new SimpleBooleanProperty(false);




	public IntegerProperty pointsProperty() {
		return points;
	}

	public ObjectProperty<SpeedLevel> speedProperty() {
		return speed;
	}

	public BooleanProperty collisionProperty() {
		return collision;
	}

	public ObjectProperty<Status> gameloopStatusProperty() {
		return gameloopStatus;
	}

	public IntegerProperty gridSizeProperty() {
		return gridSize;
	}


	public BooleanProperty highscoreWindowOpenProperty() {
		return highscoreWindowOpen;
	}

	public BooleanProperty newHighscoreWindowOpenProperty() {
		return newHighscoreWindowOpen;
	}

	public BooleanProperty aboutWindowOpenProperty() {
		return aboutWindowOpen;
	}

}