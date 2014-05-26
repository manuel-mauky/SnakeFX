package eu.lestard.snakefx.view.panel;


import de.saxsys.jfx.mvvm.api.ViewModel;
import eu.lestard.snakefx.core.SpeedLevel;
import eu.lestard.snakefx.viewmodel.CentralViewModel;
import javafx.animation.Animation;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PanelViewModel implements ViewModel {

    private static final String LABEL_START = "Start";
    private static final String LABEL_RESUME = "Resume";
    private static final String LABEL_PAUSE = "Pause";

    private ObservableList<SpeedLevel> speedLevels = FXCollections.observableArrayList();

    private StringProperty playPauseButtonText = new SimpleStringProperty(LABEL_START);

    private CentralViewModel centralViewModel;

    public PanelViewModel(CentralViewModel centralViewModel){
        this.centralViewModel = centralViewModel;

        speedLevels.addAll(SpeedLevel.values());

        centralViewModel.gameloopStatus.addListener((observable, oldStatus, newStatus) -> {
            if(Animation.Status.STOPPED.equals(newStatus)){
                playPauseButtonText.set(LABEL_START);
            }
        });
    }

    public void togglePlayPause(){
        final Animation.Status status = centralViewModel.gameloopStatus.get();
        switch (status) {
        case PAUSED:
            playPauseButtonText.set(LABEL_PAUSE);
            centralViewModel.gameloopStatus.set(Animation.Status.RUNNING);
            break;
        case RUNNING:
            playPauseButtonText.set(LABEL_RESUME);
            centralViewModel.gameloopStatus.set(Animation.Status.PAUSED);
            break;
        case STOPPED:
            playPauseButtonText.set(LABEL_PAUSE);
            centralViewModel.gameloopStatus.set(Animation.Status.RUNNING);
            break;
        }
    }

    public ObservableList<SpeedLevel> speedLevels(){
        return speedLevels;
    }

    public ObservableStringValue playPauseButtonText(){
        return playPauseButtonText;
    }

    public ObservableStringValue pointsLabelText(){
        return centralViewModel.points.asString();
    }

    public ObservableBooleanValue playPauseButtonDisabled(){
        return centralViewModel.collision;
    }

    public ObjectProperty<SpeedLevel> selectedSpeedLevel(){
        return centralViewModel.speed;
    }
}
