package eu.lestard.snakefx.inject;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import eu.lestard.snakefx.view.ApplicationStarter;
import eu.lestard.snakefx.view.MainController;

/**
 * The MainInjector is the Class that manages all instances of all classes and
 * the creation process.
 *
 * @author manuel.mauky
 *
 */
public class MainInjector {

	private static final String MAIN_FXML_FILENAME = "/fxml/main.fxml";

	private ApplicationStarter starter;

	/**
	 * Creates the object graph for the application with Dependency Injection.
	 */
	public void createObjectGraph() {
		FXMLLoader fxmlLoader = new FXMLLoader();
		FxmlFactory fxmlFactory = new FxmlFactory(fxmlLoader);
		MainController mainController = new MainController();
		Parent root = fxmlFactory.getFxmlRoot(MAIN_FXML_FILENAME, mainController);
		Scene mainScene = new Scene(root);
		starter = new ApplicationStarter(mainScene);
	}

	public ApplicationStarter getApplicationStarter(){
		return starter;
	}




}
