package pe;

import org.jetbrains.annotations.NotNull;

public class IntStack {
    private final int[] stack = new int [3];
    private int index = 0;

    public boolean isEmpty() {
        return index == 0;
    }

    public void push(int value) {
        stack[index++] = value;
    }
    public int pop() {
        return stack[--index];
    }

    public static @NotNull IntStack of(int @NotNull ... values) {
        final IntStack stack = new IntStack();
        for (int value : values) {
            stack.push(value);
        }
        return stack;
    }
}
