package eu.lestard.snakefx.core;


import eu.lestard.snakefx.viewmodel.ViewModel;
import javafx.animation.Animation.Status;
import javafx.animation.Timeline;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

import static org.assertj.core.api.Assertions.*;

public class GameLoopTest {

	private GameLoop gameLoop;
	private ViewModel viewModel;


	@Before
	public void setup() {
		viewModel = new ViewModel();
		viewModel.speed.set(SpeedLevel.SLOW);
		gameLoop = new GameLoop(viewModel);
	}


	@Test
	public void testStoppedTimelineStaysStoppedAfterSpeedChange() {
		assertThat(getTimeline().getStatus()).isEqualTo(Status.STOPPED);

		viewModel.speed.set(SpeedLevel.FAST);

		assertThat(getTimeline().getStatus()).isEqualTo(Status.STOPPED);
	}

	@Test
	public void testPlayingTimelineStaysPlayingAfterSpeedChange() {
		getTimeline().play();
		assertThat(getTimeline().getStatus()).isEqualTo(Status.RUNNING);

		viewModel.speed.set(SpeedLevel.FAST);


		assertThat(getTimeline().getStatus()).isEqualTo(Status.RUNNING);
	}

	@Test
	public void testTimelineIsPlayingAfterChangeInViewModel() {
		assertThat(viewModel.gameloopStatus.get()).isEqualTo(Status.STOPPED);
		assertThat(getTimeline().getStatus()).isEqualTo(Status.STOPPED);

		viewModel.gameloopStatus.set(Status.RUNNING);

		assertThat(getTimeline().getStatus()).isEqualTo(Status.RUNNING);
	}


	@Test
	public void testPlayingTimelineIsStoppedAfterChangeInViewModel() {
		assertThat(viewModel.gameloopStatus.get()).isEqualTo(Status.STOPPED);

		getTimeline().play();
		assertThat(getTimeline().getStatus()).isEqualTo(Status.RUNNING);
		assertThat(viewModel.gameloopStatus.get()).isEqualTo(Status.RUNNING);

		viewModel.gameloopStatus.set(Status.PAUSED);

		assertThat(getTimeline().getStatus()).isEqualTo(Status.PAUSED);
	}


	/**
	 * Helper method to get the internal {@link Timeline} instance of the
	 * gameloop.
	 */
	private Timeline getTimeline() {
		return (Timeline) Whitebox.getInternalState(gameLoop, "timeline");
	}
}
