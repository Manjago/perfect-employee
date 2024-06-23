package pe;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

import static java.awt.event.KeyEvent.VK_ENTER;

public class Typer {
    private final Robot robot;
    private final int delayFrom;
    private final int delayTo;
    private final Random random;

    public Typer(int delayFrom,
            int delayTo) throws AWTException {
        this.delayFrom = delayFrom;
        this.delayTo = delayTo;
        robot = new Robot();
        robot.setAutoWaitForIdle(true);
        random = new SecureRandom(seedFromLong(new Date().getTime()));
    }

    public void typeLine(@NotNull String line) {
        for (char c : line.toCharArray()) {
            if (allowed(c)) {
                handleAllowed(c);
            } else {
                handleNonStandard(c);
            }
        }
        robot.keyPress(VK_ENTER);
        robot.keyRelease(VK_ENTER);
        robot.delay(delay());
    }

    public void clean() {
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.delay(delay());
        robot.keyPress(KeyEvent.VK_DELETE);
        robot.keyPress(KeyEvent.VK_DELETE);
        robot.delay(delay());
    }

    public void delay(int ms) {
        robot.delay(ms);
    }

    private void handleNonStandard(char c) {
        typeUnmappedChar(c);
    }

    private void handleAllowed(char c) {
        int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);

        if (KeyEvent.CHAR_UNDEFINED == keyCode) {
            handleNonStandard(c);
        } else {
            robot.keyPress(keyCode);
            robot.keyRelease(keyCode);
            robot.delay(delay());
        }
    }

    private int delay() {
        return delayFrom + random.nextInt(delayTo - delayFrom);
    }

    private void typeUnmappedChar(char c) {
        final String text = String.valueOf(c);
        final StringSelection stringSelection = new StringSelection(text);
        final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, stringSelection);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.delay(delay());
    }

    @Contract(value = "_ -> new", pure = true)
    private byte @NotNull [] seedFromLong(long longValue) {
        return new byte[]{(byte) longValue, (byte) (longValue >> 8), (byte) (longValue >> 16),
                (byte) (longValue >> 24), (byte) (longValue >> 32), (byte) (longValue >> 40),
                (byte) (longValue >> 48), (byte) (longValue >> 56)};
    }

    private boolean allowed(char c) {
        if (c == ' ' || c == '\t') {
            return true;
        }
        return c >= 'a' && c <= 'z';
    }
}
