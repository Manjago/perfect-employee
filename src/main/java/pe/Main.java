package pe;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, AWTException {
        run(false, 500, 5000);
        //testRun(Paths.get("/home/ADMSK/kdtemnen/pe/ewallet-engine/ewallet-svfe-adapter/src/main/java/ru/bpc/ewallet/adapter/svfe/utils/Iso8583Utils.java"));
    }

    private static void run(boolean testMode, int delayFrom, int delayTo) throws AWTException, IOException {
        final RandomSource randomSource = new RandomSource();
        final String rootFileName = System.getProperty("user.home") + File.separator + "pe";
        final Path rootPath = Paths.get(rootFileName);
        if (!Files.exists(rootPath)) {
            System.out.println("Where is my work data path " + rootFileName + "?");
            System.exit(1);
        }
        if (!Files.isDirectory(rootPath)) {
            System.out.println("My work data path " + rootFileName + " must be directory");
            System.exit(2);
        }
        final Config config = new Config(rootFileName, Collections.singletonList(".java"));
        final CharToKey charToKey = new CharToKey();
        final Typer typer = new Typer(randomSource, charToKey, delayFrom, delayTo);

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
            if (testMode) {
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
