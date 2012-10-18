package eu.lestard.snakefx.config;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

public class ConfiguratorTest {

	private Configurator configurator;

	@Before
	public void setup() {
		configurator = new Configurator();
	}

	/**
	 * Verify the basic behavior of the {@link Configurator} class.
	 */
	@Test
	public void testConfigurator() {
		// The DEFAUL value for Integers has to be zero
		assertThat(IntegerKey.DEFAULT.getDefaultValue()).isEqualTo(0);

		Integer value = configurator.getValue(IntegerKey.DEFAULT);

		assertThat(value).isEqualTo(0);

		// Now we reset the value to 5
		configurator.setValue(IntegerKey.DEFAULT, 5);

		value = configurator.getValue(IntegerKey.DEFAULT);

		assertThat(value).isEqualTo(5);
	}

}