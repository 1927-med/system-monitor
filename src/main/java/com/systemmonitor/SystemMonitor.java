package main.java.com.systemmonitor;

public class SystemMonitor {
    public static void main(String[] args) {
        System.out.println("System Monitor is running...");
        // Initialize components, start monitoring, etc.
        MonitoringService monitoringService = new MonitoringService();
        monitoringService.startMonitoring();
    }
    
}
