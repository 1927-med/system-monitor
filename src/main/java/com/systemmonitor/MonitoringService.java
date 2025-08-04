package main.java.com.systemmonitor;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

import main.java.com.systemmonitor.Metric;
import main.java.com.systemmonitor.InMemoryDataStorage;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

import main.java.com.systemmonitor.Alerting;
import java.io.File;

public class MonitoringService {
    public void startMonitoring() {
        // Call the monitor methods
        monitorServerPerformance();
        monitorApplicationPerformance();
    }

    private void monitorServerPerformance() {
        // get the current CPU usage, memory usage, and disk usage
        double cpuUsage = getCpuUsage();
        double memoryUsage = getMemoryUsage();
        double diskUsage = getDiskUsage();

        // create metrics for CPU usage, memory usage, and disk usage
        Metric cpuMetric = new Metric("CPU Usage", cpuUsage, System.currentTimeMillis());
        Metric memoryMetric = new Metric("Memory Usage", memoryUsage, System.currentTimeMillis());
        Metric diskMetric = new Metric("Disk Usage", diskUsage, System.currentTimeMillis());

        // store the metrics in the data storage
        InMemoryDataStorage dataStorage = new InMemoryDataStorage();
        dataStorage.storeMetric(cpuMetric);
        dataStorage.storeMetric(memoryMetric);
        dataStorage.storeMetric(diskMetric);

        // check if the server's performance exceeds certain thresholds
        if (cpuUsage > 80 || memoryUsage > 80 || diskUsage > 80) {
            // send an alert
            Alerting alerting = new Alerting();
            alerting.sendAlert("Server performance exceeded thresholds");
        }
    }

    private void monitorApplicationPerformance() {
        // get the current response time, error rates, and resource usage
        double responseTime = getResponseTime();
        double errorRate = getErrorRate();
        double resourceUsage = getResourceUsage();

        // create metrics for response time, error rates, and resource usage
        Metric responseTimeMetric = new Metric("Response Time", responseTime, System.currentTimeMillis());
        Metric errorRateMetric = new Metric("Error Rate", errorRate, System.currentTimeMillis());
        Metric resourceUsageMetric = new Metric("Resource Usage", resourceUsage, System.currentTimeMillis());

        // store the metrics in the data storage
        InMemoryDataStorage dataStorage = new InMemoryDataStorage();
        dataStorage.storeMetric(responseTimeMetric);
        dataStorage.storeMetric(errorRateMetric);
        dataStorage.storeMetric(resourceUsageMetric);

        // check if the application's performance exceeds certain thresholds
        if (responseTime > 500 || errorRate > 10 || resourceUsage > 80) {
            // send an alert
            Alerting alerting = new Alerting();
            alerting.sendAlert("Application performance exceeded thresholds");
        }
    }

    // Note: These methods are not implemented in this example
    private double getCpuUsage() {
        OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        return operatingSystemMXBean.getSystemLoadAverage();
        
    }

    private double getMemoryUsage() {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
    MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
    return (heapMemoryUsage.getUsed() * 100.0) / heapMemoryUsage.getMax();
    }

    private double getDiskUsage() {
        File file = new File("/");
        return ((file.getTotalSpace() - file.getFreeSpace()) * 100.0) / file.getTotalSpace();
    }

    private double getResponseTime() {
        // This method is not directly related to system monitoring, 
    // it's more related to application performance monitoring.
    // For simplicity, assume we have a method to get the average response time.
    // depending on your needs, you would need to implement this method based on your application's requirements.
    return 200; // Average response time in milliseconds
    }

    private double getErrorRate() {
      // This method is not directly related to system monitoring, 
    // it's more related to application performance monitoring.
    // For simplicity, assume we have a method to get the average response time.
    // depending on your needs, you would need to implement this method based on your application's requirements.
        return 5; // Error rate in percentage
    }

    private double getResourceUsage() {
       // This method is not directly related to system monitoring, 
    // it's more related to application performance monitoring.
    // For simplicity, assume we have a method to get the average response time.
    // depending on your needs, you would need to implement this method based on your application's requirements.
        return 60; // Resource usage in percentage (assumption is 60%)
    }
}