package eu.lestard.snakefx.core;

import eu.lestard.snakefx.viewmodel.CentralViewModel;
import javafx.animation.Animation;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.util.Duration;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is the game loop of the game.
 *
 * @author manuel.mauky
 */
@Singleton
public class GameLoop {

    private static final int ONE_SECOND = 1000;

    private Timeline timeline;


    private final List<Runnable> actions = new ArrayList<>();

    private final CentralViewModel viewModel;

    public GameLoop(final CentralViewModel viewModel) {
        this.viewModel = viewModel;
        viewModel.collision.addListener(new CollisionListener());
        viewModel.speed.addListener(new SpeedChangeListener());
        viewModel.gameloopStatus.addListener(new StatusChangedListener());

        init();
    }


    /**
     * Added Actions are called on every keyframe of the GameLoop. The order of invocation is not guaranteed.
     *
     * @param action the action that gets called.
     */
    public void addAction(final Runnable action) {
        this.actions.add(action);
    }

    /**
     * Initialize the timeline instance.
     */
    private void init() {
        timeline = new Timeline(buildKeyFrame());
        timeline.setCycleCount(Animation.INDEFINITE);

        // in this place we can't use a direct binding as the ViewModel property
        // can also be changed in other places.
        timeline.statusProperty().addListener((observable, oldStatus, newStatus) ->
            viewModel.gameloopStatus.set(newStatus));
    }

    /**
     * This method creates a {@link KeyFrame} instance according to the configured framerate.
     */
    private KeyFrame buildKeyFrame() {

        final int fps = viewModel.speed.get().getFps();
        final Duration duration = Duration.millis(ONE_SECOND / fps);

        return new KeyFrame(duration, event ->
            actions.forEach(Runnable::run));
    }


    /**
     * This listener controls the timeline when there are external changes to the status property. This needs to be done
     * because the {@link Timeline#statusProperty()} is readonly and can't be bound bidirectional.
     */
    private final class StatusChangedListener implements ChangeListener<Status> {
        @Override
        public void changed(final ObservableValue<? extends Status> arg0, final Status oldStatus,
                            final Status newStatus) {

            switch (newStatus) {
                case PAUSED:
                    pause();
                    break;
                case RUNNING:
                    play();
                    break;
                case STOPPED:
                    stop();
                    break;
            }
        }
    }

    /**
     * This listener controls the timeline when the desired speed has changed.
     */
    private final class SpeedChangeListener implements ChangeListener<SpeedLevel> {
        @Override
        public void changed(final ObservableValue<? extends SpeedLevel> arg0, final SpeedLevel oldSpeed,
                            final SpeedLevel newSpeed) {

            final Status oldStatus = timeline.getStatus();

            if (Status.RUNNING.equals(oldStatus)) {
                pause();
            }

            init();

            if (Status.RUNNING.equals(oldStatus)) {
                play();
            }
        }
    }

    /**
     * This listener stops the timeline when an collision is detected.
     */
    private final class CollisionListener implements ChangeListener<Boolean> {
        @Override
        public void changed(final ObservableValue<? extends Boolean> arg0, final Boolean oldValue,
                            final Boolean newCollision) {
            if (newCollision) {
                stop();
            }
        }
    }

    private void play() {
        timeline.play();
    }

    private void pause() {
        timeline.pause();
    }

    private void stop() {
        timeline.stop();
    }

}