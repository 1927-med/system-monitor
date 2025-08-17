package com.systemmonitor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigManager {
    private static final Path CONFIG_FILE = Paths.get("config.properties");
    private final Properties properties = new Properties();

    public ConfigManager() {
        loadConfig();
    }

    private void loadConfig() {
        if (Files.exists(CONFIG_FILE)) {
            try {
                properties.load(Files.newInputStream(CONFIG_FILE));
            } catch (IOException e) {
                Logging.logError("Failed to load configuration", e);
            }
        }
        setDefaults();
    }

    private void setDefaults() {
        // Platform-specific defaults
        if (PlatformUtils.isWindows()) {
            properties.putIfAbsent("network.primary", "Ethernet");
            properties.putIfAbsent("disk.monitor", "C:\\\\");
        } else if (PlatformUtils.isLinux()) {
            properties.putIfAbsent("network.primary", "eth0");
            properties.putIfAbsent("disk.monitor", "/");
        } else { // macOS
            properties.putIfAbsent("network.primary", "en0");
            properties.putIfAbsent("disk.monitor", "/");
        }
        
        // Common defaults
        properties.putIfAbsent("cpu.threshold", "90");
        properties.putIfAbsent("memory.threshold", "85");
        properties.putIfAbsent("disk.threshold", "95");
        properties.putIfAbsent("network.threshold", "80");
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public void saveConfig() {
        try {
            properties.store(Files.newOutputStream(CONFIG_FILE), 
                "System Monitor Configuration");
        } catch (IOException e) {
            Logging.logError("Failed to save configuration", e);
        }
    }

}
