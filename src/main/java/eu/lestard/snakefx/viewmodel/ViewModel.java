package eu.lestard.snakefx.viewmodel;

import static eu.lestard.snakefx.config.IntegerConfig.ROW_AND_COLUMN_COUNT;
import javafx.animation.Animation.Status;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import eu.lestard.snakefx.core.Direction;
import eu.lestard.snakefx.core.SpeedLevel;

/**
 * This class is the central viewmodel that contains the current state of the
 * applications main properties.
 * 
 * @author manuel.mauky
 * 
 */
public class ViewModel {

	public final IntegerProperty points = new SimpleIntegerProperty(0);

	public final ObjectProperty<SpeedLevel> speed = new SimpleObjectProperty<>(SpeedLevel.MEDIUM);

	public final BooleanProperty collision = new SimpleBooleanProperty(false);

	public final ObjectProperty<Status> gameloopStatus = new SimpleObjectProperty<>(Status.STOPPED);

	public final IntegerProperty gridSize = new SimpleIntegerProperty(ROW_AND_COLUMN_COUNT.get());

	public final BooleanProperty highscoreWindowOpen = new SimpleBooleanProperty(false);

	public final BooleanProperty newHighscoreWindowOpen = new SimpleBooleanProperty(false);

	public final BooleanProperty aboutWindowOpen = new SimpleBooleanProperty(false);

	public final ObjectProperty<Direction> snakeDirection = new SimpleObjectProperty<>(Direction.UP);
}