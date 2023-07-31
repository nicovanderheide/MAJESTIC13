package parser;

import data.Team;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class CrewReader {
    public static Team read(final Path file) throws IOException {
        try (InputStream inputStream = Files.newInputStream(file)) {
            Yaml yaml = new Yaml(new Constructor(Team.class));
            return yaml.load(inputStream);
        }
    }
}
