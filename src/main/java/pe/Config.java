package pe;

public class Config {
    private final String root;
    private final String ext;

    public Config(String root,
            String ext) {
        this.root = root;
        this.ext = ext;
    }

    public String getExt() {
        return ext;
    }

    public String getRoot() {
        return root;
    }
}
