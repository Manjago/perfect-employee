package pe;

import org.jetbrains.annotations.Nullable;

import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.VK_0;
import static java.awt.event.KeyEvent.VK_1;
import static java.awt.event.KeyEvent.VK_2;
import static java.awt.event.KeyEvent.VK_3;
import static java.awt.event.KeyEvent.VK_4;
import static java.awt.event.KeyEvent.VK_5;
import static java.awt.event.KeyEvent.VK_6;
import static java.awt.event.KeyEvent.VK_7;
import static java.awt.event.KeyEvent.VK_8;
import static java.awt.event.KeyEvent.VK_9;
import static java.awt.event.KeyEvent.VK_CLOSE_BRACKET;
import static java.awt.event.KeyEvent.VK_OPEN_BRACKET;
import static java.awt.event.KeyEvent.VK_SHIFT;
import static java.awt.event.KeyEvent.VK_SLASH;

public class CharToKey {

    private final IntList QUESTION = IntList.of(VK_SHIFT,VK_SLASH);
    private final IntList EMPTY = IntList.of();

    public IntList toKeys(char c) {
        if (nonPrintable(c)) {
            return EMPTY;
        }
        final Character.UnicodeBlock block = Character.UnicodeBlock.of(c);
        if (block == Character.UnicodeBlock.BASIC_LATIN) {
            return latinChar(c);
        } else if (block == Character.UnicodeBlock.CYRILLIC) {
            return cyrillicChar(c);
        } else {
            return unmappedChar(c);
        }
    }

    public boolean nonPrintable(char c) {
        return c == 127 || c != 9 && c < 32;
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
            case '}': return IntList.of(VK_SHIFT, VK_CLOSE_BRACKET);
            case ')': return IntList.of(VK_SHIFT, VK_0);
            case '!': return IntList.of(VK_SHIFT, VK_1);
            case '@': return IntList.of(VK_SHIFT, VK_2);
            case '#': return IntList.of(VK_SHIFT, VK_3);
            case '$': return IntList.of(VK_SHIFT, VK_4);
            case '%': return IntList.of(VK_SHIFT, VK_5);
            case '^': return IntList.of(VK_SHIFT, VK_6);
            case '&': return IntList.of(VK_SHIFT, VK_7);
            case '*': return IntList.of(VK_SHIFT, VK_8);
            case '(': return IntList.of(VK_SHIFT, VK_9);
            case '_': return IntList.of(VK_SHIFT, KeyEvent.getExtendedKeyCodeForChar('-'));
            case '"': return IntList.of(VK_SHIFT, KeyEvent.getExtendedKeyCodeForChar('\''));
            case ':': return IntList.of(VK_SHIFT, KeyEvent.getExtendedKeyCodeForChar(';'));
            case '+': return IntList.of(VK_SHIFT, KeyEvent.getExtendedKeyCodeForChar('='));
            case '>': return IntList.of(VK_SHIFT, KeyEvent.getExtendedKeyCodeForChar('.'));
            case '<': return IntList.of(VK_SHIFT, KeyEvent.getExtendedKeyCodeForChar(','));
            case '|': return IntList.of(VK_SHIFT, KeyEvent.getExtendedKeyCodeForChar('\\'));
            case '?': return IntList.of(VK_SHIFT, KeyEvent.getExtendedKeyCodeForChar('/'));
            case '~': return IntList.of(VK_SHIFT, KeyEvent.getExtendedKeyCodeForChar('`'));
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
