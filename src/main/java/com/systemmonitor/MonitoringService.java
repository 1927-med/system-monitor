package com.systemmonitor;
import javafx.application.Platform;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.io.File;

public class MonitoringService {

    private final double[] cpuUsageValues = new double[100];
    private final double[] memoryUsageValues = new double[100];
    private final double[] diskUsageValues = new double[100];
    private final double[] responseTimeValues = new double[100];
    private final double[] errorRateValues = new double[100];
    private final double[] resourceUsageValues = new double[100];
    private int index = 0;
    public void startMonitoring() {
        // Initialize JavaFX platform if not already running
        if (!Platform.isFxApplicationThread()) {
            new Thread(() -> Platform.runLater(this::monitor)).start();
        } else {
            monitor();
        }
    }

    private void monitor() {
        monitorServerPerformance();
        monitorApplicationPerformance();
        
        // Print current metrics
        System.out.println("CPU Usage: " + getCpuUsage() + "%");
        System.out.println("Memory Usage: " + getMemoryUsage() + "%");
        System.out.println("Disk Usage: " + getDiskUsage() + "%");
    }

    private void monitorServerPerformance() {
        double cpuUsage = getCpuUsage();
        double memoryUsage = getMemoryUsage();
        double diskUsage = getDiskUsage();

        // Store values
        cpuUsageValues[index] = cpuUsage;
        memoryUsageValues[index] = memoryUsage;
        diskUsageValues[index] = diskUsage;

        // Create and show graphs
        Platform.runLater(() -> {
            Graphing graphing = new Graphing();
            graphing.createGraph("CPU Usage", cpuUsageValues);
            graphing.createGraph("Memory Usage", memoryUsageValues);
            graphing.createGraph("Disk Usage", diskUsageValues);
        });

        // Store metrics
        HistoricalData historicalData = new HistoricalData();
        historicalData.storeMetric("CPU Usage", cpuUsage);
        historicalData.storeMetric("Memory Usage", memoryUsage);
        historicalData.storeMetric("Disk Usage", diskUsage);

        // Check thresholds
        if (cpuUsage > 80 || memoryUsage > 80 || diskUsage > 80) {
            new Alerting().sendAlert("Server performance exceeded thresholds");
        }

        index = (index + 1) % cpuUsageValues.length;
    }

    private void monitorApplicationPerformance() {
        double responseTime = getResponseTime();
        double errorRate = getErrorRate();
        double resourceUsage = getResourceUsage();

        responseTimeValues[index] = responseTime;
        errorRateValues[index] = errorRate;
        resourceUsageValues[index] = resourceUsage;

        Platform.runLater(() -> {
            Graphing graphing = new Graphing();
            graphing.createGraph("Response Time", responseTimeValues);
            graphing.createGraph("Error Rate", errorRateValues);
            graphing.createGraph("Resource Usage", resourceUsageValues);
        });

        HistoricalData historicalData = new HistoricalData();
        historicalData.storeMetric("Response Time", responseTime);
        historicalData.storeMetric("Error Rate", errorRate);
        historicalData.storeMetric("Resource Usage", resourceUsage);

        if (responseTime > 500 || errorRate > 10 || resourceUsage > 80) {
            new Alerting().sendAlert("Application performance exceeded thresholds");
        }
    }

    // Metric collection methods
    private double getCpuUsage() {
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        return osBean.getSystemLoadAverage() * 100.0 / osBean.getAvailableProcessors();
    }

    private double getMemoryUsage() {
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        return (memoryBean.getHeapMemoryUsage().getUsed() * 100.0) / 
               memoryBean.getHeapMemoryUsage().getMax();
    }

    private double getDiskUsage() {
        File file = new File("/");
        return ((file.getTotalSpace() - file.getFreeSpace()) * 100.0) / file.getTotalSpace();
    }

    private double getResponseTime() {
        // Simulated response time
        return 200 + (Math.random() * 100);
    }

    private double getErrorRate() {
        // Simulated error rate
        return 5 + (Math.random() * 3);
    }

    private double getResourceUsage() {
        // Simulated resource usage
        return 60 + (Math.random() * 20);
    }
}