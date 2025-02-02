package org.example.manager;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestPropertyManager {

    private final Properties properties = new Properties();

    private static TestPropertyManager INSTANCE = null;

    private TestPropertyManager() {
        loadApplicationProperties();
    }

    public static TestPropertyManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TestPropertyManager();
        }
        return INSTANCE;
    }

    private void loadApplicationProperties() {
        try {
            properties.load(new FileInputStream(
                    "src/main/resources/" +
                            System.getProperty("propFile", "application") + ".properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
