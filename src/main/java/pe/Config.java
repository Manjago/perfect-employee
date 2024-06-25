package pe;

import java.util.List;

public class Config {
    private final String root;
    private final List<String> ext;

    public Config(String root,
            List<String> ext) {
        this.root = root;
        this.ext = ext;
    }

    public List<String> getExt() {
        return ext;
    }

    public String getRoot() {
        return root;
    }
}
