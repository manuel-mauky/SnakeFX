package eu.lestard.snakefx.highscore;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.codehaus.jackson.map.type.TypeFactory;

import javax.inject.Singleton;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

/**
 * DAO implementation for {@link HighScoreEntry} that is using a JSON for persistence.
 *
 * @author manuel.mauky
 */
@Singleton
public class HighscoreJsonDao implements HighscoreDao {

    private static final String HIGHSCORE_FILENAME = "highscores.json";

    private final Path filepath;
    private final ObjectMapper mapper;
    private final TypeFactory typeFactory;

    public HighscoreJsonDao() {
        filepath = Paths.get(HIGHSCORE_FILENAME);
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
        if (filepath.toFile().exists()) {
            try {
                return mapper.<List<HighScoreEntry>>readValue(filepath.toFile(),
                        typeFactory.constructCollectionType(List.class, HighScoreEntry.class));
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return Collections.emptyList();
    }

}
