package pe;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

public class RandomSource {
    private final Random random;

    public RandomSource() {
        long seed = new Date().getTime();
        random = new SecureRandom(longToBytes(seed));
    }

    public int nextInt(int bound) {
        return random.nextInt(bound);
    }

    @Contract(value = "_ -> new", pure = true)
    private byte @NotNull [] longToBytes(long value) {
        byte[] bytes = new byte[8];
        for (int i = 0; i < 8; i++) {
            bytes[i] = (byte) (value >> (i * 8));
        }
        return bytes;
    }
}