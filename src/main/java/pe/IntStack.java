package pe;

import java.util.Arrays;

public class IntStack {
    private final int[] stack = new int[3];
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

    @Override
    public String toString() {
        return "IntStack{" + "stack=" + Arrays.toString(Arrays.copyOf(stack, index)) + ", index=" + index + '}';
    }}
