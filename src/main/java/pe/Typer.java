package pe;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.VK_ENTER;

public class Typer {
    private final RandomSource randomSource;
    private final CharToKey charToKey;
    private final Robot robot;
    private final int delayFrom;
    private final int delayTo;
    private static final IntList SELECT_ALL = IntList.of(KeyEvent.VK_CONTROL, KeyEvent.VK_A);

    public Typer(RandomSource randomSource, CharToKey charToKey, int delayFrom, int delayTo) throws AWTException {
        this.randomSource = randomSource;
        this.charToKey = charToKey;
        this.delayFrom = delayFrom;
        this.delayTo = delayTo;
        this.robot = new Robot();
        robot.setAutoWaitForIdle(true);
    }

    /**
     * Types a line of text, character by character.
     *
     * @param line    the line of text to type
     * @param lineNum the line number (for error reporting)
     */
    public void typeLine(@NotNull String line, int lineNum) {
        for (char c : line.toCharArray()) {
            final IntList keys = charToKey.toKeys(c);
            try {
                type(keys);
            } catch (BadKeyException e) {
                System.out.println("Bad key: " + e + " for line '" + line + "' at " + lineNum + " for char " + c);
            }
        }
        singlePressKey(VK_ENTER);
    }

    /**
     * Cleans the current input by selecting all text and deleting it.
     *
     * @param delayClean the delay before cleaning
     */
    public void clean(int delayClean) {
        robot.delay(delayClean);
        type(SELECT_ALL);
        singlePressKey(KeyEvent.VK_DELETE);
    }

    /**
     * Presses and releases a single key.
     *
     * @param keyCode the key code of the key to press
     */
    private void singlePressKey(int keyCode) {
        robot.keyPress(keyCode);
        robot.keyRelease(keyCode);
        robot.delay(getRandomDelay());
    }

    /**
     * Delays the robot for a specified amount of milliseconds.
     *
     * @param ms the delay in milliseconds
     */
    public void delay(int ms) {
        robot.delay(ms);
    }

    /**
     * Gets a random delay between delayFrom and delayTo.
     *
     * @return the random delay
     */
    private int getRandomDelay() {
        return delayFrom + randomSource.nextInt(delayTo - delayFrom);
    }

    /**
     * Types a sequence of keys.
     *
     * @param keys the sequence of keys to type
     */
    public void type(@NotNull IntList keys) {
        final IntStack release = new IntStack();
        int vk = -1;

        try {
            for (int i = 0; i < keys.size(); i++) {
                vk = keys.get(i);
                robot.keyPress(vk);
                release.push(vk);
            }
        } catch (IllegalArgumentException e) {
            throw new BadKeyException(e, vk, keys);
        } finally {
            while (!release.isEmpty()) {
                final int keycode = release.pop();
                robot.keyRelease(keycode);
                robot.delay(getRandomDelay());
            }
        }
    }

    /**
     * Exception thrown when a bad key is encountered.
     */
    public static class BadKeyException extends RuntimeException {
        private final int keyCode;
        private final IntList keys;

        public BadKeyException(Throwable cause, int keyCode, IntList keys) {
            super(cause);
            this.keyCode = keyCode;
            this.keys = keys;
        }

        @Override
        public String toString() {
            return "BadKeyException{" +
                    "keyCode=" + keyCode +
                    ", keys=" + keys +
                    "} " + super.toString();
        }
    }
}