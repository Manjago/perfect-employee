package pe;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class ConfigLoader {

    public Config loadConfig(@Nullable String configFileName) throws IOException {
        try (final InputStream is = this.getClass().getClassLoader().getResourceAsStream("default.properties")) {
            final Properties defaultProperties = new Properties();
            defaultProperties.load(is);

            if (configFileName == null) {
                return properties2Config(defaultProperties);
            }

            final Properties enrichedProperties = enrichByAppProperties(defaultProperties, configFileName);
            return properties2Config(enrichedProperties);
        }
    }

    private Properties enrichByAppProperties(@NotNull Properties defaultProperties,
            @NotNull String appConfigPath) throws IOException {
        final InputStream inputStream;
        try {
            inputStream = Files.newInputStream(Paths.get(appConfigPath));
        } catch (IOException ignored) {
            System.out.println("File not found: " + appConfigPath + ", use defaults");
            return defaultProperties;
        }

        final Properties enrichedProps = new Properties(defaultProperties);
        enrichedProps.load(inputStream);
        return enrichedProps;
    }

    @Contract("_ -> new")
    private @NotNull Config properties2Config(@NotNull Properties properties) {
        String root = properties.getProperty("root");
        if (root == null) {
            root = System.getProperty("user.home") + File.separator + "pe";
        }

        final List<String> ext = Arrays.stream(properties.getProperty("ext").split(",")).collect(Collectors.toList());

        return new Config(root, ext, Boolean.parseBoolean(properties.getProperty("testmode")),
                Integer.parseInt(properties.getProperty("delay.from")),
                Integer.parseInt(properties.getProperty("delay.to")),
                Integer.parseInt(properties.getProperty("delay.initial"))
                );
    }
}
