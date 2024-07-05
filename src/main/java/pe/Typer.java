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
        robot = new Robot();
        robot.setAutoWaitForIdle(true);
    }

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

    public void clean(int delayClean) {
        robot.delay(delayClean);
        type(SELECT_ALL);
        singlePressKey(KeyEvent.VK_DELETE);
    }

    private void singlePressKey(int keyCode) {
        robot.keyPress(keyCode);
        robot.keyRelease(keyCode);
        robot.delay(getRandomDelay());
    }

    public void delay(int ms) {
        robot.delay(ms);
    }

    private int getRandomDelay() {
        return delayFrom + randomSource.nextInt(delayTo - delayFrom);
    }

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
            return "BadKeyException{" + "keyCode=" + keyCode + ", keys=" + keys + "} " + super.toString();
        }
    }
}
