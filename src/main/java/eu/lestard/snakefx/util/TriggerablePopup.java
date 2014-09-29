package eu.lestard.snakefx.util;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TriggerablePopup {

    private Stage stage;

    private BooleanProperty trigger = new SimpleBooleanProperty();

    public TriggerablePopup(Class<? extends FxmlView<? extends ViewModel>> contentViewType, Stage parent){
        this(contentViewType);
        stage.initOwner(parent);
    }


    public TriggerablePopup(Class<? extends FxmlView<? extends ViewModel>> contentViewType){
        stage = new Stage();

        final ViewTuple<FxmlView<? extends ViewModel>, ViewModel> viewTuple = FluentViewLoader.fxmlView(contentViewType).load();

        stage.setScene(new Scene(viewTuple.getView()));
        stage.initModality(Modality.WINDOW_MODAL);

        trigger.addListener((observable, oldValue, open) -> {
            System.out.println(">" + open);
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
