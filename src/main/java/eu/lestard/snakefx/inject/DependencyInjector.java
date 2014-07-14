package eu.lestard.snakefx.inject;

import eu.lestard.grid.GridModel;
import eu.lestard.snakefx.core.*;
import eu.lestard.snakefx.highscore.HighscoreDao;
import eu.lestard.snakefx.highscore.HighscoreJsonDao;
import eu.lestard.snakefx.highscore.HighscoreManager;
import eu.lestard.snakefx.util.KeyboardHandler;
import eu.lestard.snakefx.view.about.AboutViewModel;
import eu.lestard.snakefx.view.highscore.HighscoreViewModel;
import eu.lestard.snakefx.view.highscore.NewHighscoreViewModel;
import eu.lestard.snakefx.view.main.MainViewModel;
import eu.lestard.snakefx.view.menu.MenuViewModel;
import eu.lestard.snakefx.view.panel.PanelViewModel;
import eu.lestard.snakefx.viewmodel.CentralViewModel;
import javafx.application.HostServices;
import javafx.util.Callback;

import java.util.HashMap;
import java.util.Map;

public class DependencyInjector implements Callback<Class<?>, Object> {

    private final Map<Class<?>, Object> instances = new HashMap<>();

    /**
     * Create a new Instance of the Dependency Injector.
     * This initializes all instances that are handled by this class.
     *
     * @param hostServices the HostServices instance is a dependency that only the JavaFX application root can obtain.
     *                     Therefore it needs to be passes as argument to the DependencyInjector.
     */
    public DependencyInjector(HostServices hostServices) {
        put(HostServices.class, hostServices);

        injectCore();

        injectOthers();

        injectViewModels();
    }

    private void injectCore() {
        final GridModel<State> gridModel = new GridModel<>();

        final CentralViewModel viewModel = new CentralViewModel();
        final GameLoop gameLoop = new GameLoop(viewModel);
        final Snake snake = new Snake(viewModel, gridModel, gameLoop);
        final FoodGenerator foodGenerator = new FoodGenerator(viewModel, gridModel);
        final NewGameFunction newGameFunction = new NewGameFunction(viewModel, gridModel, snake, foodGenerator);

        put(GridModel.class, gridModel);
        put(CentralViewModel.class, viewModel);
        put(GameLoop.class, gameLoop);
        put(Snake.class, snake);
        put(FoodGenerator.class, foodGenerator);
        put(NewGameFunction.class, newGameFunction);
    }

    private void injectViewModels() {
        final CentralViewModel centralViewModel = get(CentralViewModel.class);

        final MainViewModel mainViewModel = new MainViewModel(get(GridModel.class), get(NewGameFunction.class));
        final MenuViewModel menuViewModel = new MenuViewModel(centralViewModel, get(NewGameFunction.class));

        final PanelViewModel panelViewModel = new PanelViewModel(centralViewModel);

        final HighscoreViewModel highscoreViewModel = new HighscoreViewModel(centralViewModel, get(HighscoreManager.class));

        final NewHighscoreViewModel newHighscoreViewModel = new NewHighscoreViewModel(centralViewModel, get(HighscoreManager.class));

        final AboutViewModel aboutViewModel = new AboutViewModel(get(HostServices.class));

        put(MainViewModel.class, mainViewModel);
        put(MenuViewModel.class, menuViewModel);
        put(PanelViewModel.class, panelViewModel);
        put(HighscoreViewModel.class, highscoreViewModel);
        put(NewHighscoreViewModel.class, newHighscoreViewModel);
        put(AboutViewModel.class, aboutViewModel);
    }

    private void injectOthers() {
        final KeyboardHandler keyboardHandler = new KeyboardHandler(get(CentralViewModel.class));
        final HighscoreDao highscoreDao = new HighscoreJsonDao();
        final HighscoreManager highscoreManager = new HighscoreManager(highscoreDao);

        put(KeyboardHandler.class, keyboardHandler);
        put(HighscoreDao.class, highscoreDao);
        put(HighscoreManager.class, highscoreManager);
    }


    private <T> void put(Class<T> clazz, T instance) {
        instances.put(clazz, instance);
    }

    public <T> T get(Class<T> clazz) {
        if (instances.containsKey(clazz)) {
            return (T) instances.get(clazz);
        } else {
            try {
                final T newInstance = clazz.newInstance();
                put(clazz, newInstance);
                return newInstance;
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(
                    "Can't inject instance of type [" + clazz.getName() + "]. " +
                        "There is no injector mapping and it can't be created with class.newInstance()", e);
            }
        }
    }

    @Override
    public Object call(Class<?> clazz) {
        return this.get(clazz);
    }
}
