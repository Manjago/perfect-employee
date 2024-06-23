package pe;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws IOException, AWTException {
        final Config config = new Config("src/main/java/pe/Main.java");
        final Path path = Paths.get(config.getFile());
        final Typer typer = new Typer(50, 100);

        long totalLines = 0;
        long totalCharacters = 0;
        typer.delay(1000);
        while (!Thread.interrupted()) {
            try (BufferedReader reader = Files.newBufferedReader(path)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    totalCharacters += typer.typeLine(line);
                    ++totalLines;
                    System.out.printf("\rPrinted %,18d lines, %,18d characters", totalLines, totalCharacters);
                }
            }
            //typer.clean();
            break;
        }
    }
}
