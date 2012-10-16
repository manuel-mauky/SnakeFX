package eu.lestard.snakefx.core;

import static org.fest.assertions.api.Assertions.assertThat;
import javafx.scene.control.Label;

import org.junit.Before;
import org.junit.Test;

public class PointsManagerTest {

	private PointsManager manager;

	private Label pointsLabel;

	@Before
	public void setup() {

		pointsLabel = new Label();

		manager = new PointsManager();

		manager.init(pointsLabel);
	}

	@Test
	public void testPointsManager() {

		assertThat(manager.getPoints()).isEqualTo(0);
		assertThat(pointsLabel.getText()).contains("0");

		manager.addPoint();

		assertThat(manager.getPoints()).isEqualTo(1);
		assertThat(pointsLabel.getText()).contains("1");

		manager.newGame();

		assertThat(manager.getPoints()).isEqualTo(0);
		assertThat(pointsLabel.getText()).contains("0");

	}
}