package pe;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class ConfigLoader {

    public Config loadConfig(@Nullable String configFileName) throws IOException {
        // Load default properties from the resource file
        try (final InputStream is = this.getClass().getClassLoader().getResourceAsStream("default.properties")) {
            final Properties defaultProperties = new Properties();
            defaultProperties.load(is);

            // If no config file name is provided, use default properties
            if (configFileName == null) {
                return propertiesToConfig(defaultProperties);
            }

            // Enrich default properties with application-specific properties
            final Properties enrichedProperties = enrichByAppProperties(defaultProperties, configFileName);
            return propertiesToConfig(enrichedProperties);
        }
    }

    private Properties enrichByAppProperties(@NotNull final Properties defaultProperties, @NotNull final String appConfigPath) throws IOException {
        // Enrich default properties with properties from the specified application config file
        try (InputStream inputStream = Files.newInputStream(Paths.get(appConfigPath))) {
            final Properties enrichedProps = new Properties(defaultProperties);
            enrichedProps.load(inputStream);
            return enrichedProps;
        } catch (NoSuchFileException ignored) {
            // If the specified config file is not found, use default properties
            System.out.println("File not found: " + appConfigPath + ", use defaults");
            return defaultProperties;
        }
    }

    @Contract("_ -> new")
    private @NotNull Config propertiesToConfig(@NotNull final Properties properties) {
        // Load properties and construct the Config object
        String root = properties.getProperty("root");
        if (root == null) {
            root = System.getProperty("user.home") + File.separator + "pe";
        }

        final List<String> ext = Arrays.stream(properties.getProperty("ext").split(","))
                .collect(Collectors.toList());

        return new Config(root, ext,
                Integer.parseInt(properties.getProperty("delay.from")),
                Integer.parseInt(properties.getProperty("delay.to")),
                Integer.parseInt(properties.getProperty("delay.initial")),
                Integer.parseInt(properties.getProperty("delay.clean")));
    }
}