package com.systemmonitor;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsoleLogger {
    private static final Logger LOG = Logger.getLogger(ConsoleLogger.class.getName());

    public void log(Level level, String message) {
        LOG.log(level, message);
    }
    
}
