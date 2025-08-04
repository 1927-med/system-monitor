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

    private double[] cpuUsageValues = new double[100];
    private double[] memoryUsageValues = new double[100];
    private double[] diskUsageValues = new double[100];
    private double[] responseTimeValues = new double[100];
    private double[] errorRateValues = new double[100];
    private double[] resourceUsageValues = new double[100];
    private int index = 0;
    public void startMonitoring() {
        // Call the monitor methods
        monitorServerPerformance();
        monitorApplicationPerformance();
         // Retrieve system metrics
         double cpuUsage = getCpuUsage();
         double memoryUsage = getMemoryUsage();
         double diskUsage = getDiskUsage();
 
         // Print system metrics
         System.out.println("CPU usage: " + cpuUsage + "%");
         System.out.println("Memory usage: " + memoryUsage + "%");
         System.out.println("Disk usage: " + diskUsage + "%");


    }

    private void monitorServerPerformance() {
        // get the current CPU usage, memory usage, and disk usage
        double cpuUsage = getCpuUsage();
        double memoryUsage = getMemoryUsage();
        double diskUsage = getDiskUsage();

        // Storing the values in an arrays
        cpuUsageValues[index] = cpuUsage;
        memoryUsageValues[index] = memoryUsage;
        diskUsageValues[index] = diskUsage;
        
        // Increment the index
        index = (index + 1) % cpuUsageValues.length;


        

        Graphing graphing = new Graphing();
        graphing.createGraph("CPU Usage", cpuUsageValues);
        graphing.createGraph("Memory Usage", memoryUsageValues);
        graphing.createGraph("Disk Usage", diskUsageValues);

        HistoricalData historicalData = new HistoricalData();
        historicalData.storeMetric("CPU Usage", cpuUsage);
        historicalData.storeMetric("Memory Usage", memoryUsage);
        historicalData.storeMetric("Disk Usage", diskUsage);

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

        // Store the values in the arrays
        responseTimeValues[index] = responseTime;
        errorRateValues[index] = errorRate;
        resourceUsageValues[index] = resourceUsage;

        // Increment the index
        index = (index + 1) % responseTimeValues.length;

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

        Graphing graphing = new Graphing();
        graphing.createGraph("Response Time", responseTimeValues);
        graphing.createGraph("Error Rate", errorRateValues);
        graphing.createGraph("Resource Usage", resourceUsageValues);

        HistoricalData historicalData = new HistoricalData();
        historicalData.storeMetric("Response Time", responseTime);
        historicalData.storeMetric("Error Rate", errorRate);
        historicalData.storeMetric("Resource Usage", resourceUsage);
    }

    // Note for anyone reading my code: These methods are not implemented for every different case this is a trial example 
    // that i am trying out on my computer and may have some errors and also may not work for your pc unless you change some 
    // stuff in the code in this class and maybe others as well. 
    private double getCpuUsage() {
        OperatingSystemMXBean operatingSystemMXBean =  ManagementFactory.getOperatingSystemMXBean();
        double systemLoadAverage = operatingSystemMXBean.getSystemLoadAverage();
        return systemLoadAverage * 100.0 / operatingSystemMXBean.getAvailableProcessors();
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