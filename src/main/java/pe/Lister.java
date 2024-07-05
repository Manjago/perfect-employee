package pe;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Lister {
    private final String root;
    private final List<String> suffixes;

    public Lister(String root, List<String> suffixes) {
        this.root = root;
        this.suffixes = suffixes;
    }

    public void allFiles(Job job) throws IOException {
        final Path rootPath = Paths.get(root);
        listAllFiles(rootPath, job);
    }

    private void listAllFiles(Path currentPath, Job job) throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(currentPath)) {
            for (Path entry : stream) {
                if (Files.isDirectory(entry)) {
                    listAllFiles(entry, job);
                } else if (matchesSuffix(entry.getFileName().toString())) {
                    job.increment();
                    if (job.isLimitReached()) {
                        job.setFoundedPath(entry);
                        return;
                    }
                }
            }
        }
    }

    private boolean matchesSuffix(@NotNull String fileName) {
        return suffixes.stream().anyMatch(fileName::endsWith);
    }

    public static class Job {
        private final int limit;
        private int current = 0;
        private Path foundedPath;

        public Job(int limit) {
            this.limit = limit;
        }

        public void increment() {
            ++current;
        }

        private boolean isLimitReached() {
            return current == limit;
        }

        public Path getFoundedPath() {
            return foundedPath;
        }

        private void setFoundedPath(Path path) {
            this.foundedPath = path;
        }

        public int getCurrent() {
            return current;
        }

        @Override
        public String toString() {
            return "Job{" +
                    "limit=" + limit +
                    ", current=" + current +
                    ", foundedPath=" + foundedPath +
                    '}';
        }
    }
}