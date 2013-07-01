package eu.lestard.snakefx.inject;

import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXMLLoader;
import javafx.util.Callback;
import eu.lestard.snakefx.view.controller.KeyboardController;
import eu.lestard.snakefx.view.controller.MainController;
import eu.lestard.snakefx.view.controller.MenuController;
import eu.lestard.snakefx.view.controller.PanelController;
import eu.lestard.snakefx.viewmodel.ViewModel;


/**
 * This class is the Dependency Injector for all Controller classes for the
 * view.
 * 
 * This class acts as controllerFactory for the {@link FXMLLoader}. See
 * {@link FXMLLoader#setControllerFactory(Callback)}.
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

		final KeyboardController keyboardController = new KeyboardController(viewModel);
		instances.put(KeyboardController.class, keyboardController);
	}

	@Override
	public Object call(final Class<?> clazz) {
		return instances.get(clazz);
	}

	@SuppressWarnings("unchecked")
	public <T> T callSafe(final Class<T> clazz) {
		return (T) instances.get(clazz);
	}

}
