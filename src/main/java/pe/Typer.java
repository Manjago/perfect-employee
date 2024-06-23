package pe;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import static java.awt.event.KeyEvent.VK_ENTER;

public class Typer {
    private final RandomSource randomSource;
    private final Robot robot;
    private final int delayFrom;
    private final int delayTo;
    private final Set<Character> ALLOWED = new HashSet<>();

    public Typer(RandomSource randomSource,
            int delayFrom,
            int delayTo) throws AWTException {
        this.randomSource = randomSource;
        this.delayFrom = delayFrom;
        this.delayTo = delayTo;
        robot = new Robot();
        robot.setAutoWaitForIdle(true);
        for (char c : " .[];0123456789\\/".toCharArray()) {
            ALLOWED.add(c);
        }
    }

    public long typeLine(@NotNull String line) {
        long counter = 0;
        for (char c : line.toCharArray()) {
            if (allowed(c)) {
                handleAllowed(c);
            } else {
                handleNonStandard(c);
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
        return delayFrom + randomSource.nextInt(delayTo - delayFrom);
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

    private boolean allowed(char c) {
        if (ALLOWED.contains(c)) {
            return true;
        }
        return c >= 'a' && c <= 'z';
    }
}
