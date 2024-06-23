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

        long total = 0;
        typer.delay(1000);
        while (!Thread.interrupted()) {
            try (BufferedReader reader = Files.newBufferedReader(path)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    total += typer.typeLine(line);
                    System.out.printf("%nPrinted %,18d characters", total);
                }
            }
            //typer.clean();
            break;
        }
    }
}
