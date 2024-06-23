package pe;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, AWTException {
        final RandomSource randomSource = new RandomSource();
        final Config config = new Config("src", ".java");
        final Typer typer = new Typer(randomSource, 50, 100);

        long totalLines = 0;
        long totalCharacters = 0;
        typer.delay(1000);
        while (!Thread.interrupted()) {

            final Lister lister = new Lister(config.getRoot(), config.getExt());
            final List<Path> paths = lister.allFiles();
            if (paths.isEmpty()) {
                return;
            }

            final int index = randomSource.nextInt(paths.size());
            final Path currentPath = paths.get(index);
            System.out.println(currentPath);

            try (BufferedReader reader = Files.newBufferedReader(currentPath)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    totalCharacters += typer.typeLine(line);
                    ++totalLines;
                    System.out.printf("\rPrinted %,18d lines, %,18d characters", totalLines, totalCharacters);
                }
            }
            typer.clean();
            System.out.printf("\rTotal   %,18d lines, %,18d characters%n", totalLines, totalCharacters);
        }
    }
}
