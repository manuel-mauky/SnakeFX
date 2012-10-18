package eu.lestard.snakefx.core;

/**
* Represents the different levels of speed for the game loop. The speed is
* stored as Frames per Second.
*
* @author manuel.mauky
*
*/
public enum SpeedLevel {
	SLOW(2),

	MEDIUM(5),

	FAST(15),

	EXTRA(30);

	private int fps;

	private SpeedLevel(final int fps) {
		this.fps = fps;
	}

	public int getFps() {
		return fps;
	}

}