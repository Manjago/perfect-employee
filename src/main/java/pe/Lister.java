package pe;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Lister {
    private final String root;
    private final String suffix;

    public Lister(String root,
            String suffix) {
        this.root = root;
        this.suffix = suffix;
    }

    public List<Path> allFiles() throws IOException {
        final List<Path> result = new ArrayList<>();
        final Path rootPath = Paths.get(root);
        listAllFiles(rootPath, result);
        return result;
    }

    private void listAllFiles(Path currentPath, List<Path> allFiles)
            throws IOException
    {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(currentPath))
        {
            for (Path entry : stream) {
                if (Files.isDirectory(entry)) {
                    listAllFiles(entry, allFiles);
                } else {
                    if (entry.getFileName().toString().endsWith(suffix)) {
                        allFiles.add(entry);
                    }
                }
            }
        }
    }
}
