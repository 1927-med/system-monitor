package com.systemmonitor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logging {

    private static final Path LOG_FILE = Paths.get("system_monitor.log");
    private static final DateTimeFormatter TIMESTAMP_FORMAT = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void log(String message) {
        String logEntry = String.format("[%s] %s%n", 
            LocalDateTime.now().format(TIMESTAMP_FORMAT), 
            message);
        
        try {
            Files.write(LOG_FILE, logEntry.getBytes(), 
                StandardOpenOption.CREATE, 
                StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Failed to write to log file: " + e.getMessage());
        }
    }

    public static void logError(String message, Throwable throwable) {
        log(String.format("ERROR: %s - %s", message, throwable.getMessage()));
    }
    
}
