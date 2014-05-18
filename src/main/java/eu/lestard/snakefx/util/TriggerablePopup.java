package eu.lestard.snakefx.util;

import de.saxsys.jfx.mvvm.api.ViewModel;
import de.saxsys.jfx.mvvm.base.view.View;
import de.saxsys.jfx.mvvm.viewloader.ViewLoader;
import de.saxsys.jfx.mvvm.viewloader.ViewTuple;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TriggerablePopup {

    private Stage stage;

    private BooleanProperty trigger = new SimpleBooleanProperty();

    public TriggerablePopup(Class<? extends View<? extends ViewModel>> contentViewType, Stage parent){
        this(contentViewType);
        stage.initOwner(parent);
    }

    public TriggerablePopup(Class<? extends View<? extends ViewModel>> contentViewType){
        stage = new Stage();

        ViewLoader viewLoader = new ViewLoader();

        final ViewTuple<View<ViewModel>, ViewModel> viewTuple = viewLoader.loadViewTuple((Class<? extends View<ViewModel>>) contentViewType);

        stage.setScene(new Scene(viewTuple.getView()));
        stage.initModality(Modality.WINDOW_MODAL);

        trigger.addListener((observable, oldValue, open) -> {
            if (open) {
                stage.show();
            } else {
                stage.hide();
            }
        });
        stage.showingProperty().addListener((observable, oldValue, showing)->{
            if(!showing){
                trigger.set(false);
            }
        });
    }

    public BooleanProperty trigger(){
        return trigger;
    }

    public Stage getStage() {
        return stage;
    }
}
