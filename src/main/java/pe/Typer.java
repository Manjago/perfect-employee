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

    public Typer(RandomSource randomSource, CharToKey charToKey, int delayFrom, int delayTo) throws AWTException {
        this.randomSource = randomSource;
        this.charToKey = charToKey;
        this.delayFrom = delayFrom;
        this.delayTo = delayTo;
        robot = new Robot();
        robot.setAutoWaitForIdle(true);
    }

    public long typeLine(@NotNull String line, int lineNum) {
        long counter = 0;
        for (char c : line.toCharArray()) {
            final IntList keys = charToKey.toKeys(c);
            try {
                type(keys);
            } catch (BadKeyException e) {
                System.out.println("Bad key: " + e + " for line '" + line + "' at " + lineNum + " for char " + c);
            }
            ++counter;
        }
        robot.keyPress(VK_ENTER);
        robot.keyRelease(VK_ENTER);
        robot.delay(delay());
        return counter;
    }

    public void clean() {
        robot.delay(5000);
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

    private int delay() {
        return delayFrom + randomSource.nextInt(delayTo - delayFrom);
    }

    public void type(@NotNull IntList keys) {
        final IntStack release = new IntStack();
        for (int i = 0; i < keys.size(); i++) {
            final int vk = keys.get(i);
            try {
                robot.keyPress(vk);
            } catch (IllegalArgumentException e) {
                throw new BadKeyException(e, vk, keys);
            }
            release.push(vk);
        }
        while (!release.isEmpty()) {
            final int keycode = release.pop();
            robot.keyRelease(keycode);
            robot.delay(delay());
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
