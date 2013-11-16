package eu.lestard.snakefx.util;

import eu.lestard.snakefx.view.FXMLFile;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PopupDialogHelper {

    private FxmlFactory fxmlFactory;

    public PopupDialogHelper(FxmlFactory fxmlFactory) {
        this.fxmlFactory = fxmlFactory;
    }

    public Stage createModalDialog(BooleanProperty trigger, Stage parent, FXMLFile fxmlFile){
        Stage stage = new Stage();
        stage.setScene(new Scene(fxmlFactory.getFxmlRoot(fxmlFile)));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(parent);

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

        return stage;
    }



}
