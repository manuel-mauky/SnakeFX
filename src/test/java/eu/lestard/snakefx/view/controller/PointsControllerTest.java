package eu.lestard.snakefx.view.controller;

import static org.fest.assertions.api.Assertions.assertThat;
import javafx.scene.control.Label;

import org.junit.Before;
import org.junit.Test;

import eu.lestard.snakefx.view.controller.PointsController;

public class PointsControllerTest {

	private PointsController controller;

	private Label pointsLabel;

	@Before
	public void setup() {

		pointsLabel = new Label();

		controller = new PointsController(pointsLabel);

	}

	@Test
	public void testPointsController() {

		assertThat(controller.getPoints()).isEqualTo(0);
		assertThat(pointsLabel.getText()).contains("0");

		controller.addPoint();

		assertThat(controller.getPoints()).isEqualTo(1);
		assertThat(pointsLabel.getText()).contains("1");

		controller.newGame();

		assertThat(controller.getPoints()).isEqualTo(0);
		assertThat(pointsLabel.getText()).contains("0");

	}
}