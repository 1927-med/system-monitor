package com.systemmonitor;

public class MonitoringService {
    public void startMonitoring() {
        // This is now handled in SystemMonitor.java
        new Alerting().sendAlert("Monitoring started");
    }
}