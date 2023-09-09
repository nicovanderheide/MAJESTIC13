package generators;

import lombok.extern.slf4j.Slf4j;
import roster.RosterBuilderUtil;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static java.nio.file.Files.walk;

@Slf4j
public final class CrewGenerator {
    private CrewGenerator() {
    }

    public static void main(final String[] args) throws IOException {
        log.info("Crew generator");
        final Path path = Paths.get(".");
        try (Stream<Path> paths = walk(path)) {
            paths.filter(f -> f.toString().endsWith(".yml")).forEach(RosterBuilderUtil::generate);
        }
    }
}
