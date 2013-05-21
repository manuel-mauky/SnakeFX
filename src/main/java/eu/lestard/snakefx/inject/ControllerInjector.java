package eu.lestard.snakefx.inject;

import java.util.HashMap;
import java.util.Map;

import javafx.util.Callback;

import eu.lestard.snakefx.view.controller.AboutController;
import eu.lestard.snakefx.view.controller.HighScoreController;
import eu.lestard.snakefx.view.controller.HighscoresController;
import eu.lestard.snakefx.view.controller.MainController;
import eu.lestard.snakefx.view.controller.MenuController;
import eu.lestard.snakefx.view.controller.NewHighscoreController;
import eu.lestard.snakefx.view.controller.PanelController;
import eu.lestard.snakefx.viewmodel.ViewModel;


/**
 * This class is the Dependency Injector for all Controller classes for the
 * view.
 * 
 * @author manuel.mauky
 * 
 */
public class ControllerInjector implements Callback<Class<?>, Object> {

	private final Map<Class<?>, Object> instances = new HashMap<>();

	public ControllerInjector(final ViewModel viewModel, final CoreInjector coreInjector) {

		final MainController mainController = new MainController(viewModel, coreInjector.getGrid(),
				coreInjector.getNewGameFunction());
		instances.put(MainController.class, mainController);


		final MenuController menuController = new MenuController(viewModel, coreInjector.getNewGameFunction());
		instances.put(MenuController.class, menuController);


		final PanelController panelController = new PanelController(viewModel);
		instances.put(PanelController.class, panelController);

		final AboutController aboutController = new AboutController();
		instances.put(AboutController.class, aboutController);

		final HighscoresController highscoresController = new HighscoresController();
		instances.put(HighScoreController.class, highscoresController);

		final NewHighscoreController newHighscoreController = new NewHighscoreController();
		instances.put(NewHighscoreController.class, newHighscoreController);
	}

	@Override
	public Object call(final Class<?> clazz) {
		return instances.get(clazz);
	}

}
