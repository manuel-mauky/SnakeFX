package eu.lestard.snakefx.inject;

import eu.lestard.snakefx.core.*;
import eu.lestard.snakefx.highscore.HighscoreDao;
import eu.lestard.snakefx.highscore.HighscoreJsonDao;
import eu.lestard.snakefx.highscore.HighscoreManager;
import eu.lestard.snakefx.util.KeyboardHandler;
import eu.lestard.snakefx.view.presenter.*;
import eu.lestard.snakefx.view.viewmodels.MainViewModel;
import eu.lestard.snakefx.viewmodel.CentralViewModel;
import javafx.util.Callback;

import java.util.HashMap;
import java.util.Map;

public class DependencyInjector implements Callback<Class<?>, Object> {

    private final Map<Class<?>, Object> instances = new HashMap<>();

    public DependencyInjector() {
        injectCore();

        injectOthers();

        injectPresenter();
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

    private void injectPresenter() {
        final CentralViewModel viewModel = get(CentralViewModel.class);

        final MainViewModel mainViewModel = new MainViewModel(viewModel, get(Grid.class),
                get(NewGameFunction.class));
        final MenuPresenter menuPresenter = new MenuPresenter(viewModel, get(NewGameFunction.class));
        final PanelPresenter panelPresenter = new PanelPresenter(viewModel);

        final HighscorePresenter highscorePresenter = new HighscorePresenter(viewModel, get(HighscoreManager.class));

        final NewScoreEntryPresenter newScoreEntryPresenter = new NewScoreEntryPresenter(get(HighscoreManager.class), viewModel);

        put(MainViewModel.class, mainViewModel);
        put(MenuPresenter.class, menuPresenter);
        put(PanelPresenter.class, panelPresenter);
        put(HighscorePresenter.class, highscorePresenter);
        put(NewScoreEntryPresenter.class, newScoreEntryPresenter);
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
                System.out.println("newInstance");
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
