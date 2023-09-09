package runner;

import generators.CrewGenerator;
import generators.MissionGenerator;

import java.io.IOException;

public final class Run {
    private Run() {
    }

    public static void main(final String[] args) throws IOException {
        CrewGenerator.main(args);
        MissionGenerator.main(args);
    }
}
