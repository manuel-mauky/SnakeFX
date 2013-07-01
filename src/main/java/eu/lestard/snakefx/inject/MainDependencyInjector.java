package eu.lestard.snakefx.inject;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import eu.lestard.snakefx.view.ApplicationStarter;
import eu.lestard.snakefx.view.FXMLFile;
import eu.lestard.snakefx.view.controller.KeyboardController;
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

		final ViewModel viewModel = new ViewModel();
		final CoreInjector coreInjector = new CoreInjector(viewModel);

		final ControllerInjector controllerInjector = new ControllerInjector(viewModel, coreInjector);

		final FxmlFactory fxmlFactory = new FxmlFactory(controllerInjector);

		final Parent root = fxmlFactory.getFxmlRoot(FXMLFile.MAIN);

		final Scene mainScene = new Scene(root);

		final KeyboardController keyboardController = controllerInjector.callSafe(KeyboardController.class);
		mainScene.setOnKeyPressed(keyboardController);

		starter = new ApplicationStarter(mainScene, primaryStage);
	}

	public ApplicationStarter getApplicationStarter() {
		return starter;
	}

}
