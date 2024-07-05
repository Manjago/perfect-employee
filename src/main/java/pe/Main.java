package pe;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        try {
            run(args);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(6);
        }
    }

    /**
     * Runs the application with the provided arguments.
     *
     * @param args the command line arguments
     * @throws IOException  if an I/O error occurs
     * @throws AWTException if an AWT error occurs
     */
    private static void run(String @NotNull [] args) throws IOException, AWTException {
        final String configFileName = args.length > 0 ? args[0] : null;
        final Config config = new ConfigLoader().loadConfig(configFileName);
        run(config);
    }

    /**
     * Runs the application with the provided configuration.
     *
     * @param config the configuration
     * @throws AWTException if an AWT error occurs
     * @throws IOException  if an I/O error occurs
     */
    private static void run(@NotNull Config config) throws AWTException, IOException {
        System.out.println("Use config " + config);
        final RandomSource randomSource = new RandomSource();
        verifyRootPath(config.getRoot());
        final CharToKey charToKey = new CharToKey();
        final Typer typer = new Typer(randomSource, charToKey, config.getDelayFrom(), config.getDelayTo());

        typer.delay(config.getDelayInitial());
        while (!Thread.interrupted()) {
            loopBody(config, randomSource, typer);
        }
        System.exit(4);
    }

    /**
     * Verifies that the root path exists and is a directory.
     *
     * @param rootFileName the root path
     */
    private static void verifyRootPath(String rootFileName) {
        final Path rootPath = Paths.get(rootFileName);
        if (!Files.exists(rootPath)) {
            System.out.println("Where is my work data path " + rootFileName + "?");
            System.exit(1);
        }
        if (!Files.isDirectory(rootPath)) {
            System.out.println("My work data path " + rootFileName + " must be directory");
            System.exit(2);
        }
    }

    /**
     * The main loop body of the application.
     *
     * @param config       the configuration
     * @param randomSource the random source
     * @param typer        the typer
     * @throws IOException if an I/O error occurs
     */
    private static void loopBody(@NotNull Config config,
            @NotNull RandomSource randomSource,
            @NotNull Typer typer) throws IOException {
        final Path currentPath = chooseNextFile(config, randomSource);
        processFile(typer, currentPath);
        typer.clean(config.getDelayClean());
    }

    /**
     * Chooses the next file to process based on the configuration and random source.
     *
     * @param config       the configuration
     * @param randomSource the random source
     * @return the path of the next file to process
     * @throws IOException if an I/O error occurs
     */
    private static Path chooseNextFile(@NotNull Config config,
            @NotNull RandomSource randomSource) throws IOException {
        final Lister lister = new Lister(config.getRoot(), config.getExt());
        final Lister.Job firstJob = new Lister.Job(-1);
        lister.allFiles(firstJob);
        if (firstJob.getCurrent() < 1) {
            System.exit(3);
        }
        final int index = randomSource.nextInt(firstJob.getCurrent()) + 1;
        final Lister.Job secondJob = new Lister.Job(index);
        lister.allFiles(secondJob);
        if (secondJob.getFoundedPath() == null) {
            System.exit(5);
        }

        final Path currentPath = secondJob.getFoundedPath();
        System.out.println("Founded " + firstJob.getCurrent() + " files, select file " + index + ":" + currentPath);
        return currentPath;
    }

    /**
     * Processes the specified file by typing its content.
     *
     * @param typer       the typer
     * @param currentPath the path of the file to process
     * @throws IOException if an I/O error occurs
     */
    private static void processFile(Typer typer, Path currentPath) throws IOException {
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