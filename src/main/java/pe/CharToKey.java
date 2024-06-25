package pe;

import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.VK_SHIFT;
import static java.awt.event.KeyEvent.VK_SLASH;

public class CharToKey {

    private final IntStack QUESTION = IntStack.of(VK_SLASH, VK_SHIFT);

    public IntStack toKeys(char c) {
        final Character.UnicodeBlock block = Character.UnicodeBlock.of(c);
        if (block == Character.UnicodeBlock.BASIC_LATIN) {
            return latinChar(c);
        } else if (block == Character.UnicodeBlock.CYRILLIC) {
            return cyrillicChar(c);
        } else {
            return unmappedChar(c);
        }
    }

    private IntStack latinChar(char c) {
        final int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);

        if (KeyEvent.CHAR_UNDEFINED == keyCode) {
            return unmappedChar(c);
        } else if (Character.isUpperCase(c)) {
            return IntStack.of(keyCode, VK_SHIFT);
        } else {
            return IntStack.of(keyCode);
        }
    }

    private IntStack cyrillicChar(char c) {
        return QUESTION; // temp
    }

    private IntStack unmappedChar(char c) {
        return QUESTION;
    }
}
