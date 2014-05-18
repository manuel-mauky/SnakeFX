package eu.lestard.snakefx.inject;

import eu.lestard.snakefx.core.*;
import eu.lestard.snakefx.highscore.HighscoreDao;
import eu.lestard.snakefx.highscore.HighscoreJsonDao;
import eu.lestard.snakefx.highscore.HighscoreManager;
import eu.lestard.snakefx.util.KeyboardHandler;
import eu.lestard.snakefx.view.highscore.HighscoreViewModel;
import eu.lestard.snakefx.view.highscore.NewHighscoreViewModel;
import eu.lestard.snakefx.view.main.MainViewModel;
import eu.lestard.snakefx.view.menu.MenuViewModel;
import eu.lestard.snakefx.view.panel.PanelViewModel;
import eu.lestard.snakefx.viewmodel.CentralViewModel;
import javafx.util.Callback;

import java.util.HashMap;
import java.util.Map;

public class DependencyInjector implements Callback<Class<?>, Object> {

    private final Map<Class<?>, Object> instances = new HashMap<>();

    public DependencyInjector() {
        injectCore();

        injectOthers();

        injectViewModels();
    }

    private void injectCore() {
        final CentralViewModel viewModel = new CentralViewModel();
        final Grid grid = new Grid(viewModel);
        final GameLoop gameLoop = new GameLoop(viewModel);
        final Snake snake = new Snake(viewModel, grid, gameLoop);
        final FoodGenerator foodGenerator = new FoodGenerator(viewModel, grid);
        final NewGameFunction newGameFunction = new NewGameFunction(viewModel, grid, snake, foodGenerator);

        put(CentralViewModel.class, viewModel);
        put(Grid.class, grid);
        put(GameLoop.class, gameLoop);
        put(Snake.class, snake);
        put(FoodGenerator.class, foodGenerator);
        put(NewGameFunction.class, newGameFunction);
    }

    private void injectViewModels() {
        final CentralViewModel centralViewModel = get(CentralViewModel.class);

        final MainViewModel mainViewModel = new MainViewModel(centralViewModel, get(Grid.class),
                get(NewGameFunction.class));
        final MenuViewModel menuViewModel = new MenuViewModel(centralViewModel, get(NewGameFunction.class));

        final PanelViewModel panelViewModel = new PanelViewModel(centralViewModel);

        final HighscoreViewModel highscoreViewModel = new HighscoreViewModel(centralViewModel, get(HighscoreManager.class));

        final NewHighscoreViewModel newHighscoreViewModel = new NewHighscoreViewModel(centralViewModel, get(HighscoreManager.class));

        put(MainViewModel.class, mainViewModel);
        put(MenuViewModel.class, menuViewModel);
        put(PanelViewModel.class, panelViewModel);
        put(HighscoreViewModel.class, highscoreViewModel);
        put(NewHighscoreViewModel.class, newHighscoreViewModel);
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
        if(instances.containsKey(clazz)){
            return (T) instances.get(clazz);
        }else{
            try{
                return clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(
                    "Can't inject instance of type [" + clazz.getName() + "]. " +
                        "There is no injector mapping and it can't be created with class.newInstance()",e);
            }
        }
    }

    @Override
    public Object call(Class<?> clazz) {
        return this.get(clazz);
    }
}
