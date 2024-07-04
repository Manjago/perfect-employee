package pe;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws IOException, AWTException {
        run(args);
    }

    private static void run(String @NotNull [] args) throws IOException, AWTException {
        final String configFileName = args.length > 0 ? args[0] : null;
        final Config config = new ConfigLoader().loadConfig(configFileName);
        run(config);
    }

    private static void run(@NotNull Config config) throws AWTException, IOException {
        System.out.println("Use config " + config);
        final RandomSource randomSource = new RandomSource();
        final String rootFileName = config.getRoot();
        final Path rootPath = Paths.get(rootFileName);
        if (!Files.exists(rootPath)) {
            System.out.println("Where is my work data path " + rootFileName + "?");
            System.exit(1);
        }
        if (!Files.isDirectory(rootPath)) {
            System.out.println("My work data path " + rootFileName + " must be directory");
            System.exit(2);
        }
        final CharToKey charToKey = new CharToKey();
        final Typer typer = new Typer(randomSource, charToKey, config.getDelayFrom(), config.getDelayTo());

        typer.delay(config.getDelayInitial());
        while (!Thread.interrupted()) {
            if (loopBody(config, randomSource, typer)) {
                break;
            }
        }
        System.exit(4);
    }

    private static boolean loopBody(@NotNull Config config,
            RandomSource randomSource,
            Typer typer) throws IOException {
        final Lister lister = new Lister(config.getRoot(), config.getExt());
        final Lister.Job firstJob = new Lister.Job(-1);
        lister.allFiles(firstJob);
        if (firstJob.getCurrent() < 1) {
            System.exit(3);
        }
        final int index = randomSource.nextInt(firstJob.getCurrent() + 1) + 1;
        final Lister.Job secondJob = new Lister.Job(index);
        lister.allFiles(secondJob);
        if (secondJob.getFoundedPath() == null) {
            System.exit(5);
        }

        final Path currentPath = secondJob.getFoundedPath();
        System.out.println("Founded " + firstJob.getCurrent() + " files, select file " + index + ":" + currentPath);

        try (BufferedReader reader = Files.newBufferedReader(currentPath)) {
            String line;
            int lineNum = 0;
            while ((line = reader.readLine()) != null) {
                ++lineNum;
                typer.typeLine(line, lineNum);
            }
        }
        if (config.isTestMode()) {
            return true;
        } else {
            typer.clean();
        }
        return false;
    }

}
