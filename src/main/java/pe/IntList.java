package pe;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IntList {
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

    public String serializable() {
        final StringBuilder sb = new StringBuilder();
        for(int i = 0; i < index; ++i) {
            sb.append(',');
            sb.append(list[i]);
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "IntList{" + "list=" + Arrays.toString(list) + ", index=" + index + '}';
    }
}
