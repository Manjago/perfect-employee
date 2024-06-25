package pe;

import org.jetbrains.annotations.Nullable;

import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.VK_8;
import static java.awt.event.KeyEvent.VK_CLOSE_BRACKET;
import static java.awt.event.KeyEvent.VK_OPEN_BRACKET;
import static java.awt.event.KeyEvent.VK_SHIFT;
import static java.awt.event.KeyEvent.VK_SLASH;

public class CharToKey {

    private final IntList QUESTION = IntList.of(VK_SHIFT,VK_SLASH);

    public IntList toKeys(char c) {
        final Character.UnicodeBlock block = Character.UnicodeBlock.of(c);
        if (block == Character.UnicodeBlock.BASIC_LATIN) {
            return latinChar(c);
        } else if (block == Character.UnicodeBlock.CYRILLIC) {
            return cyrillicChar(c);
        } else {
            return unmappedChar(c);
        }
    }

    private IntList latinChar(char c) {

        final IntList pretender = specialLatin(c);
        if (pretender != null) {
            return pretender;
        }

        final int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);

        if (KeyEvent.CHAR_UNDEFINED == keyCode) {
            return unmappedChar(c);
        } else if (Character.isUpperCase(c)) {
            return IntList.of(VK_SHIFT, keyCode);
        } else {
            return IntList.of(keyCode);
        }
    }

    private @Nullable IntList specialLatin(char c) {
        switch (c) {
            case '{': return IntList.of(VK_SHIFT, VK_OPEN_BRACKET);
            case '}': return IntList.of(VK_SHIFT, VK_CLOSE_BRACKET, VK_SHIFT);
            case '*': return IntList.of(VK_SHIFT, VK_8);
            default: return null;
        }
    }

    private IntList cyrillicChar(char c) {
        return QUESTION; // temp
    }

    private IntList unmappedChar(char c) {
        return QUESTION;
    }
}
