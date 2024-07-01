package pe;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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

        long totalLines = 0;
        long totalCharacters = 0;
        typer.delay(1000);
        while (!Thread.interrupted()) {

            final Lister lister = new Lister(config.getRoot(), config.getExt());
            final List<Path> paths = lister.allFiles();
            if (paths.isEmpty()) {
                System.exit(3);
            }

            final int index = randomSource.nextInt(paths.size());
            final Path currentPath = paths.get(index);
            System.out.println(currentPath);

            try (BufferedReader reader = Files.newBufferedReader(currentPath)) {
                String line;
                int lineNum = 0;
                while ((line = reader.readLine()) != null) {
                    ++lineNum;
                    totalCharacters += typer.typeLine(line, lineNum);
                    ++totalLines;
                    System.out.printf("\rCurrent %,18d lines, %,18d characters", totalLines, totalCharacters);
                }
            }
            System.out.printf("\rPrinted %,18d lines, %,18d characters%n", totalLines, totalCharacters);
            if (config.isTestMode()) {
                break;
            } else {
                typer.clean();
            }
        }
        System.exit(4);
    }

    private static void testRun(Path currentPath) throws IOException, AWTException {
        final Typer typer = new Typer(new RandomSource(), new CharToKey(), 50, 100);
        System.out.println(currentPath);

        try (BufferedReader reader = Files.newBufferedReader(currentPath)) {
            String line;
            int lineNum = 0;
            while ((line = reader.readLine()) != null) {
                ++lineNum;
                typer.typeLine(line, lineNum);
            }
        }
    }

}
