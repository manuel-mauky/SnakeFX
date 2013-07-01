package eu.lestard.snakefx.inject;

import eu.lestard.snakefx.highscore.HighscoreJsonDao;
import eu.lestard.snakefx.highscore.HighscoreManager;
import eu.lestard.snakefx.highscore.HighscoreDao;


/**
 * This class is the dependency injector for all classes in the highscore
 * package.
 * 
 * @author manuel.mauky
 * 
 */
public class HighscoreInjector {

	private final HighscoreDao highscoreDao;

	private final HighscoreManager highscoreManager;

	public HighscoreInjector() {
		highscoreDao = new HighscoreJsonDao();

		highscoreManager = new HighscoreManager(highscoreDao);
	}

	public HighscoreDao getHighscoreDao() {
		return highscoreDao;
	}

	public HighscoreManager getHighscoreManager() {
		return highscoreManager;
	}


}
