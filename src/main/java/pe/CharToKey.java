package pe;

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

    private static final IntList QUESTION = IntList.of(VK_SHIFT, VK_SLASH);
    private static final IntList EMPTY = IntList.of();
    private final Map<Character, IntList> map = new HashMap<>();

    public CharToKey() throws IOException {
        loadMapping();
    }

    private void loadMapping() throws IOException {
        try (InputStream inputStream = Objects.requireNonNull(
                getClass().getClassLoader().getResourceAsStream("map.txt"), "Resource map.txt not found")) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = br.readLine()) != null) {
                    final String[] tokens = line.split(",");
                    if (tokens.length > 0) {
                        final char character = (char) Integer.parseInt(tokens[0]);
                        final IntList commands = IntList.of(tokens);
                        map.put(character, commands);
                    }
                }
            }
        }
    }

    public IntList toKeys(char c) {
        if (isNonPrintable(c)) {
            return EMPTY;
        }
        return map.getOrDefault(c, QUESTION);
    }

    private boolean isNonPrintable(char c) {
        return c == 127 || (c < 32 && c != 9);
    }
}