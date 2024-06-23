package pe;

import jdk.jfr.internal.LogLevel;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Typer {
    private static final Logger logger = Logger.getLogger("Typer");
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
                logger.log(Level.INFO, () -> "Start unmapped " + c);
                typeUnmappedChar(c);
                logger.log(Level.INFO, () -> "Stop unmapped " + c);
                continue;
            }
            logger.log(Level.INFO, () -> "Mapped " + c + " to " + keyCode);
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
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.delay(delay());
        robot.keyPress(KeyEvent.VK_V);
        robot.delay(delay());
        robot.keyRelease(KeyEvent.VK_V);
        robot.delay(delay());
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.delay(delay());
    }

    @Contract(value = "_ -> new", pure = true)
    private byte @NotNull [] seedFromLong(long longValue) {
        return new byte[]{(byte) longValue, (byte) (longValue >> 8), (byte) (longValue >> 16),
                (byte) (longValue >> 24), (byte) (longValue >> 32), (byte) (longValue >> 40),
                (byte) (longValue >> 48), (byte) (longValue >> 56)};
    }
}
