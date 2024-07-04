package pe;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static java.awt.event.KeyEvent.VK_SHIFT;
import static java.awt.event.KeyEvent.VK_SLASH;

public class CharToKey {

    private final static IntList QUESTION = IntList.of(VK_SHIFT, VK_SLASH);
    private final static IntList EMPTY = IntList.of();
    private final Map<Character, IntList> map = new HashMap<>();

    public CharToKey() throws IOException {
        try (final @NotNull InputStream inputStream =
                     Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("map.txt"))) {
            try (final BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = br.readLine()) != null) {
                    final String[] tokens = line.split(",");
                    final Character character = (char) Integer.parseInt(tokens[0]);
                    final IntList commands = IntList.of(tokens);
                    map.put(character, commands);
                }
            }
        }

    }

    public IntList toKeys(char c) {
        if (nonPrintable(c)) {
            return EMPTY;
        } else if (map.containsKey(c)) {
            return map.get(c);
        } else {
            return unmappedChar();
        }
    }

    public boolean nonPrintable(char c) {
        return c == 127 || c != 9 && c < 32;
    }

    private IntList unmappedChar() {
        return QUESTION;
    }
}
