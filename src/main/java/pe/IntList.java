package pe;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class IntList {
    private final int[] list = new int[3];
    private int index = 0;

    public static @NotNull IntList of(int @NotNull ... values) {
        final IntList stack = new IntList();
        for (int value : values) {
            stack.push(value);
        }
        return stack;
    }

    public int size() {
        return index;
    }

    public int get(int i) {
        return list[i];
    }

    public boolean isEmpty() {
        return index == 0;
    }

    private void push(int value) {
        list[index++] = value;
    }

    @Override
    public String toString() {
        return "IntList{" + "list=" + Arrays.toString(list) + ", index=" + index + '}';
    }
}
