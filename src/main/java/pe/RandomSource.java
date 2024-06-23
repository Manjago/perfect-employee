package pe;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

public class RandomSource {
    private final Random random;

    public RandomSource() {
        random = new SecureRandom(seedFromLong(new Date().getTime()));
    }

    public int nextInt(int bound) {
        return random.nextInt(bound);
    }

    @Contract(value = "_ -> new", pure = true)
    private byte @NotNull [] seedFromLong(long longValue) {
        return new byte[]{(byte) longValue, (byte) (longValue >> 8), (byte) (longValue >> 16),
                (byte) (longValue >> 24), (byte) (longValue >> 32), (byte) (longValue >> 40),
                (byte) (longValue >> 48), (byte) (longValue >> 56)};
    }


}
