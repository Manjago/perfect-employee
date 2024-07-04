package pe;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Lister {
    private final String root;
    private final List<String> suffix;

    public Lister(String root, List<String> suffix) {
        this.root = root;
        this.suffix = suffix;
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
                } else {
                    final String fileName = entry.getFileName().toString();
                    final boolean matched = suffix.stream().anyMatch(fileName::endsWith);
                    if (matched) {
                        job.inc();
                        if (job.current == job.limit) {
                            job.foundedPath = entry;
                            return;
                        }
                    }
                }
            }
        }
    }

    public static class Job {
        private final int limit;
        private int current = 0;
        private Path foundedPath;

        public Job(int limit) {
            this.limit = limit;
        }

        private void inc() {
            ++current;
        }

        public Path getFoundedPath() {
            return foundedPath;
        }

        public int getCurrent() {
            return current;
        }

        @Override
        public String toString() {
            return "Job{" + "limit=" + limit + ", current=" + current + ", foundedPath=" + foundedPath + '}';
        }
    }
}
