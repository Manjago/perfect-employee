package pe;

import java.util.List;

public class Config {
    private final String root;
    private final List<String> ext;
    private final boolean testMode;
    private final int delayFrom;
    private final int delayTo;
    private final int delayInitial;
    private final int delayClean;

    public Config(String root,
            List<String> ext,
            boolean testMode,
            int delayFrom,
            int delayTo,
            int delayInitial,
            int delayClean) {
        this.root = root;
        this.ext = ext;
        this.testMode = testMode;
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

    public boolean isTestMode() {
        return testMode;
    }

    public int getDelayFrom() {
        return delayFrom;
    }

    public int getDelayTo() {
        return delayTo;
    }

    public List<String> getExt() {
        return ext;
    }

    public String getRoot() {
        return root;
    }

    @Override
    public String toString() {
        return "Config{" + "root='" + root + '\'' + ", ext=" + ext + ", testMode=" + testMode + ", delayFrom=" + delayFrom + ", delayTo=" + delayTo + ", delayInitial=" + delayInitial + ", delayClean=" + delayClean + '}';
    }
}
