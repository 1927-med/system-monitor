package com.systemmonitor;

public class MonitoringService {
    public void startMonitoring() {
        // Simulated monitoring data
        double[] cpuData = {20, 45, 60, 30};
        double[] memoryData = {50, 65, 70, 60};
        
        // Update graphs
        Graphing.createGraph("CPU Usage", cpuData);
        Graphing.createGraph("Memory Usage", memoryData);
        
        // Send alert
        new Alerting().sendAlert("Monitoring started");
    }
}