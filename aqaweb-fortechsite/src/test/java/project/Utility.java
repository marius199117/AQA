package project;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Collection of utility methods.
 */
public interface Utility {

    /**
     * Get property entity from a file.
     *
     * @param filePath the file path
     * @return
     */
    static Properties getPropertiesFromFile(String filePath) {
        Properties properties = new Properties();
        try (InputStream is = Utility.class.getClassLoader().getResourceAsStream(filePath)) {
            properties.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Could not load config file");
        }
        return properties;
    }
}
