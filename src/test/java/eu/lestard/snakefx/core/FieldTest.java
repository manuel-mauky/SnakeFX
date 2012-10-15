package eu.lestard.snakefx.core;

import static org.fest.assertions.api.Assertions.assertThat;
import javafx.scene.shape.Rectangle;

import org.junit.Test;

/**
 * @author manuel.mauky
 */
public class FieldTest {


	/**
	 * When the Field is initialized, the rectangle inside of the field has to
	 * be created and the state has to be set to the default (empty).
	 */
	@Test
	public void testInitialization() {

		int x = 3;
		int y = 5;

		int sizeInPixel = 100;

		Field field = new Field(x, y, sizeInPixel);


		Rectangle rectangle = field.getRectangle();

		assertThat(rectangle.getWidth()).isEqualTo(sizeInPixel);
		assertThat(rectangle.getHeight()).isEqualTo(sizeInPixel);

		/*
		 * the x value has to be (x * sizeInPixel) because there are x other
		 * Fields on the left of this field, each with the same sizeInPixel.
		 * Same is true for the y value.
		 */
		assertThat(rectangle.getX()).isEqualTo(300);
		assertThat(rectangle.getY()).isEqualTo(500);


		assertThat(field.getX()).isEqualTo(x);
		assertThat(field.getY()).isEqualTo(y);

	}


}
