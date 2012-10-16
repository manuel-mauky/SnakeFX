package eu.lestard.snakefx.view.controller;

import javafx.scene.control.Label;

public class PointsController {

	private static final String LABEL = "Points: ";

	private int points;

	private Label pointsLabel;

	public void init(final Label pointsLabel) {
		this.pointsLabel = pointsLabel;
		update();
	}

	public void newGame() {
		points = 0;
		update();
	}

	public void addPoint() {
		points++;

		update();
	}

	public int getPoints() {
		return points;
	}

	private void update() {
		pointsLabel.setText(LABEL + points);
	}
}