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
        robot.setAutoDelay(300);
        robot.setAutoWaitForIdle(true);
        random = new SecureRandom(seedFromLong(new Date().getTime()));
    }

    public void typeLine(@NotNull String line) {
        for (char c : line.toCharArray()) {
            int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
            if (KeyEvent.CHAR_UNDEFINED == keyCode) {
                typeUnmappedChar(c);
                continue;
            }
            robot.keyPress(keyCode);
            robot.delay(delay());
            robot.keyRelease(keyCode);
            robot.delay(delay());
        }
    }

    private int delay() {
        return delayFrom + random.nextInt(delayTo - delayFrom);
    }

    private void typeUnmappedChar(char c) {
        final String text = String.valueOf(c);
        StringSelection stringSelection = new StringSelection(text);
        final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, stringSelection);
        robot.delay(delay());
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.delay(delay());
        robot.keyPress(KeyEvent.VK_V);
        robot.delay(delay());
        robot.keyRelease(KeyEvent.VK_V);
        robot.delay(delay());
        robot.keyRelease(KeyEvent.VK_CONTROL);
    }

    @Contract(value = "_ -> new", pure = true)
    private byte @NotNull [] seedFromLong(long longValue) {
        return new byte[]{(byte) longValue, (byte) (longValue >> 8), (byte) (longValue >> 16),
                (byte) (longValue >> 24), (byte) (longValue >> 32), (byte) (longValue >> 40),
                (byte) (longValue >> 48), (byte) (longValue >> 56)};
    }
}
