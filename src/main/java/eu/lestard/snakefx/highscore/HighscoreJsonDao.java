package eu.lestard.snakefx.highscore;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.codehaus.jackson.map.type.TypeFactory;

import eu.lestard.snakefx.config.StringConfig;

/**
 * DAO implementation for {@link HighScoreEntry} that is using a JSON for
 * persistence.
 * 
 * @author manuel.mauky
 * 
 */
public class HighscoreJsonDao implements HighscoreDao {

	private final Path filepath;
	private final ObjectMapper mapper;
	private final TypeFactory typeFactory;

	public HighscoreJsonDao() {
		filepath = Paths.get(StringConfig.HIGH_SCORE_FILEPATH.get());
		mapper = new ObjectMapper();
		mapper.configure(Feature.INDENT_OUTPUT, true);
		typeFactory = TypeFactory.defaultInstance();
	}

	@Override
	public void persist(final List<HighScoreEntry> highscores) {
		try {
			mapper.writeValue(filepath.toFile(), highscores);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<HighScoreEntry> load() {
		try {
			return mapper.<List<HighScoreEntry>> readValue(filepath.toFile(),
					typeFactory.constructCollectionType(List.class, HighScoreEntry.class));
		} catch (final IOException e) {
			e.printStackTrace();
		}

		return Collections.emptyList();
	}

}
