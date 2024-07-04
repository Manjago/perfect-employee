package pe;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Arrays;

public class IntList implements Serializable {
    private static final int DEPTH = 3;
    private final int[] list = new int[DEPTH];
    private int index = 0;

    public static @NotNull IntList of(int @NotNull ... values) {
        final IntList list = new IntList();
        for (int value : values) {
            list.push(value);
        }
        return list;
    }

    public static @NotNull IntList of(String @NotNull [] from1Tokens) {
        final IntList list = new IntList();
        for (int i = 1; i < from1Tokens.length; i++) {
           list.push(Integer.parseInt(from1Tokens[i]));
        }
        return list;
    }

    public int size() {
        return index;
    }

    public int get(int i) {
        return list[i];
    }

    private void push(int value) {
        list[index++] = value;
    }

    @Override
    public String toString() {
        return "IntList{" + "list=" + Arrays.toString(Arrays.copyOf(list, index)) + ", index=" + index + '}';
    }
}
