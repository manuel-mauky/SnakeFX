package eu.lestard.snakefx.inject;

import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;
import eu.lestard.snakefx.view.ApplicationStarter;
import eu.lestard.snakefx.view.FXMLFile;
import eu.lestard.snakefx.view.Keyboard;
import eu.lestard.snakefx.view.controller.MainController;
import eu.lestard.snakefx.viewmodel.ViewModel;

/**
 * The DependencyInjector is the Class that manages all instances of all classes
 * and the creation process.
 * 
 * @author manuel.mauky
 * 
 */
public class MainDependencyInjector {

	private final ApplicationStarter starter;

	public MainDependencyInjector(final Stage primaryStage) {

		ViewModel viewModel = new ViewModel();
		final CoreInjector coreInjector = new CoreInjector(viewModel);

		ControllerInjector controllerInjector = new ControllerInjector(viewModel, coreInjector);

		final FxmlFactory fxmlFactory = new FxmlFactory(controllerInjector);

		Parent root = fxmlFactory.getFxmlRoot(FXMLFile.MAIN);

		Scene mainScene = new Scene(root);

		starter = new ApplicationStarter(mainScene, primaryStage);
	}

	public ApplicationStarter getApplicationStarter() {
		return starter;
	}

}
