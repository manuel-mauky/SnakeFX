package eu.lestard.snakefx.inject;

import eu.lestard.snakefx.core.*;
import eu.lestard.snakefx.highscore.HighscoreDao;
import eu.lestard.snakefx.highscore.HighscoreJsonDao;
import eu.lestard.snakefx.highscore.HighscoreManager;
import eu.lestard.snakefx.util.KeyboardHandler;
import eu.lestard.snakefx.view.presenter.MainPresenter;
import eu.lestard.snakefx.view.presenter.MenuPresenter;
import eu.lestard.snakefx.view.presenter.PanelPresenter;
import eu.lestard.snakefx.viewmodel.ViewModel;
import javafx.util.Callback;

import java.util.HashMap;
import java.util.Map;

public class DependencyInjector implements Callback<Class<?>, Object> {

    private final Map<Class<?>, Object> instances = new HashMap<>();

    public DependencyInjector() {
        injectCore();

        injectPresenter();

        injectOthers();
    }

    private void injectCore() {
        final ViewModel viewModel = new ViewModel();
        final Grid grid = new Grid(viewModel);
        final GameLoop gameLoop = new GameLoop(viewModel);
        final Snake snake = new Snake(viewModel, grid, gameLoop);
        final FoodGenerator foodGenerator = new FoodGenerator(viewModel, grid);
        final NewGameFunction newGameFunction = new NewGameFunction(viewModel, grid, snake, foodGenerator);

        put(ViewModel.class, viewModel);
        put(Grid.class, grid);
        put(GameLoop.class, gameLoop);
        put(Snake.class, snake);
        put(FoodGenerator.class, foodGenerator);
        put(NewGameFunction.class, newGameFunction);
    }

    private void injectPresenter() {
        final ViewModel viewModel = get(ViewModel.class);

        final MainPresenter mainPresenter = new MainPresenter(viewModel, get(Grid.class),
                get(NewGameFunction.class));
        final MenuPresenter menuPresenter = new MenuPresenter(viewModel, get(NewGameFunction.class));
        final PanelPresenter panelPresenter = new PanelPresenter(viewModel);


        put(MainPresenter.class, mainPresenter);
        put(MenuPresenter.class, menuPresenter);
        put(PanelPresenter.class, panelPresenter);
    }

    private void injectOthers() {
        final KeyboardHandler keyboardHandler = new KeyboardHandler(get(ViewModel.class));
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
        return (T) instances.get(clazz);
    }

    @Override
    public Object call(Class<?> clazz) {
        return instances.get(clazz);
    }
}
