package eu.lestard.snakefx.config;

import java.util.EnumMap;
import java.util.Map;

/**
 * The Configurator is used in the application to get configuration values in a
 * type-safe way.
 *
 * It is based on a key-value system. The key's are specified in the enum
 * {@link IntegerKey}. Every key has a default value.
 *
 * @author manuel.mauky
 *
 */
public class Configurator {

	private final Map<IntegerKey, Integer> intValues = new EnumMap<IntegerKey, Integer>(
			IntegerKey.class);

	/**
	 * In the constructor the internal key-value map is initialized with the
	 * default values from the {@link IntegerKey} enum.
	 */
	public Configurator() {
		for (IntegerKey k : IntegerKey.values()) {
			intValues.put(k, k.getDefaultValue());
		}
	}

	/**
	 * Returns the saved value for the given {@link IntegerKey}.
	 *
	 * @param key
	 * @return the value for the key.
	 */
	public Integer getValue(final IntegerKey key) {
		return intValues.get(key);
	}

	/**
	 * Set a new value for the given {@link IntegerKey}.
	 *
	 * @param key
	 *            the key for which a new value has to be set
	 * @param value
	 *            the new value
	 */
	public void setValue(final IntegerKey key, final Integer value) {
		intValues.put(key, value);
	}

}