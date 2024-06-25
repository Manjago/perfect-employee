package pe;

import org.jetbrains.annotations.Nullable;

import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.VK_8;
import static java.awt.event.KeyEvent.VK_CLOSE_BRACKET;
import static java.awt.event.KeyEvent.VK_OPEN_BRACKET;
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

        final IntStack pretender = specialLatin(c);
        if (pretender != null) {
            return pretender;
        }

        final int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);

        if (KeyEvent.CHAR_UNDEFINED == keyCode) {
            return unmappedChar(c);
        } else if (Character.isUpperCase(c)) {
            return IntStack.of(keyCode, VK_SHIFT);
        } else {
            return IntStack.of(keyCode);
        }
    }

    private @Nullable IntStack specialLatin(char c) {

        switch (c) {
            case '{': return IntStack.of(VK_OPEN_BRACKET, VK_SHIFT);
            case '}': return IntStack.of(VK_CLOSE_BRACKET, VK_SHIFT);
            case '*': return IntStack.of(VK_8, VK_SHIFT);
            default: return null;
        }
    }

    private IntStack cyrillicChar(char c) {
        return QUESTION; // temp
    }

    private IntStack unmappedChar(char c) {
        return QUESTION;
    }
}
