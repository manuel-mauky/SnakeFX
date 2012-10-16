package eu.lestard.snakefx.view.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import javafx.animation.Animation.Status;

import org.junit.Before;
import org.junit.Test;

import eu.lestard.snakefx.core.GameLoop;
import eu.lestard.snakefx.core.SpeedLevel;
import eu.lestard.snakefx.view.controller.SpeedChangeController;

public class SpeedChangeControllerTest {

	private GameLoop gameLoopMock;

	private SpeedChangeController controller;


	@Before
	public void setup() {
		gameLoopMock = mock(GameLoop.class);

		controller = new SpeedChangeController(gameLoopMock, null);
	}

	@Test
	public void testChangeSpeedGameLoopIsRunning() {

		when(gameLoopMock.getStatus()).thenReturn(Status.RUNNING);

		controller.changeSpeed(SpeedLevel.FAST);

		verify(gameLoopMock).setFps(SpeedLevel.FAST.getFps());

		verify(gameLoopMock).stop();
		verify(gameLoopMock).init();

		verify(gameLoopMock).play();
	}

	@Test
	public void testChangeSpeedGameLoopIsStopped() {

		when(gameLoopMock.getStatus()).thenReturn(Status.STOPPED);

		controller.changeSpeed(SpeedLevel.FAST);

		verify(gameLoopMock).setFps(SpeedLevel.FAST.getFps());

		verify(gameLoopMock).stop();
		verify(gameLoopMock).init();

		// The play method must not be called because the gameloop was
		// stopped in the first place.
		verify(gameLoopMock, never()).play();
	}

	@Test
	public void testChangeSpeedGameLoopIsPaused() {

		when(gameLoopMock.getStatus()).thenReturn(Status.STOPPED);

		controller.changeSpeed(SpeedLevel.FAST);

		verify(gameLoopMock).setFps(SpeedLevel.FAST.getFps());

		verify(gameLoopMock).stop();
		verify(gameLoopMock).init();

		// The play method must not be called because the gameloop was
		// paused in the first place.
		verify(gameLoopMock, never()).play();
	}

}