import lombok.extern.slf4j.Slf4j;
import roster.RosterBuilder;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static java.nio.file.Files.walk;

@Slf4j
public class CrewGenerator {
    public static void main(String[] args) throws IOException {
        log.info("Crew generator");
        final Path path = Paths.get(".");
        try (Stream<Path> paths = walk(path)) {
            paths.filter(f -> f.toString().endsWith(".yml"))
                    .forEach(file -> {
                        final RosterBuilder builder = new RosterBuilder();
                        builder.create(file);
                    });
        }
    }
}
