package com.systemmonitor;

import javafx.application.Platform;
import java.lang.management.*;

public class MonitoringService {
    private final Graphing graphing = new Graphing();

    public void startMonitoring() {
        Platform.runLater(() -> {
            double cpu = getCpuUsage();
            double memory = getMemoryUsage();
            
            graphing.createGraph("CPU Usage", new double[]{cpu});
            graphing.createGraph("Memory Usage", new double[]{memory});
            
            new Alerting().sendAlert("Monitoring started");
        });
    }

    private double getCpuUsage() {
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        return osBean.getSystemLoadAverage() * 100 / osBean.getAvailableProcessors();
    }

    private double getMemoryUsage() {
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        return (memoryBean.getHeapMemoryUsage().getUsed() * 100.0) / 
               memoryBean.getHeapMemoryUsage().getMax();
    }
}