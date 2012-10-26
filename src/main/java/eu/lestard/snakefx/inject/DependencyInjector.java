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
public class DependencyInjector {

	private ApplicationStarter starter;

	private final Stage primaryStage;

	public DependencyInjector(final Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	/**
	 * Creates the object graph for the application with Dependency Injection.
	 */
	public void createObjectGraph() {

		FxmlFactory fxmlFactory = new FxmlFactory();

		StageFactory stageFactory = new StageFactory(fxmlFactory);

		CoreInjector coreInjector = new CoreInjector();

		ControllerInitializer controllerInitializer = new ControllerInitializer();

		ControllerInjector controllerInjector = new ControllerInjector(
				primaryStage, coreInjector, fxmlFactory, stageFactory,
				controllerInitializer);


		final Parent mainRoot = controllerInjector.getMainRoot();

		BindingInitializer bindingInitializer = new BindingInitializer(
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
