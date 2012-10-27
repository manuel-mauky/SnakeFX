package eu.lestard.snakefx.inject;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import eu.lestard.snakefx.view.ApplicationStarter;
import eu.lestard.snakefx.view.Keyboard;
import eu.lestard.snakefx.view.controller.MainController;

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

		final FxmlFactory fxmlFactory = new FxmlFactory();

		final StageFactory stageFactory = new StageFactory(fxmlFactory);

		final CoreInjector coreInjector = new CoreInjector();

		final ControllerInitializer controllerInitializer = new ControllerInitializer();

		final ControllerInjector controllerInjector = new ControllerInjector(
				primaryStage, coreInjector, fxmlFactory, stageFactory,
				controllerInitializer);


		final Parent mainRoot = controllerInjector.getMainRoot();

		final BindingInitializer bindingInitializer = new BindingInitializer(
				mainRoot, coreInjector, controllerInjector);

		bindingInitializer.initBindings();

		final Keyboard keyboard = new Keyboard(coreInjector.getSnake());
		final Scene mainScene = createMainScene(mainRoot, keyboard);


		final MainController mainController = controllerInjector
				.getMainController();
		mainController.newGame();

		starter = new ApplicationStarter(mainScene, primaryStage);
	}

	private Scene createMainScene(final Parent root, final Keyboard keyboard) {
		Scene mainScene = new Scene(root);
		mainScene.setOnKeyPressed(keyboard);
		return mainScene;
	}


	public ApplicationStarter getApplicationStarter() {
		return starter;
	}

}
