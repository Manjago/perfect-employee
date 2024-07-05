package pe;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Config {
    private final @NotNull String root;
    private final @NotNull List<String> ext;
    private final int delayFrom;
    private final int delayTo;
    private final int delayInitial;
    private final int delayClean;

    public Config(@NotNull String root,
            @NotNull List<String> ext,
            int delayFrom,
            int delayTo,
            int delayInitial,
            int delayClean) {
        this.root = root;
        this.ext = ext;
        this.delayFrom = delayFrom;
        this.delayTo = delayTo;
        this.delayInitial = delayInitial;
        this.delayClean = delayClean;
    }

    public int getDelayClean() {
        return delayClean;
    }

    public int getDelayInitial() {
        return delayInitial;
    }

    public int getDelayFrom() {
        return delayFrom;
    }

    public int getDelayTo() {
        return delayTo;
    }

    public @NotNull List<String> getExt() {
        return ext;
    }

    public @NotNull String getRoot() {
        return root;
    }

    @Override
    public String toString() {
        return "Config{" +
                "root='" + root + '\'' +
                ", ext=" + ext +
                ", delayFrom=" + delayFrom +
                ", delayTo=" + delayTo +
                ", delayInitial=" + delayInitial +
                ", delayClean=" + delayClean +
                '}';
    }
}