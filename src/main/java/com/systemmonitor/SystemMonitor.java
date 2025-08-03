package main.java.com.systemmonitor;

public class SystemMonitor {
    public static void main(String[] args) {
        System.out.println("System Monitor is running...");
        // Initialize components, start monitoring, etc.
        // This is where the main logic of the system monitor would be implemented.
        // For example, you might want to initialize the data storage, start collecting metrics,
        // and handle alerts.
        InMemoryDataStorage dataStorage = new InMemoryDataStorage();
        Alerting alerting = new Alerting();

         
        
         // Collect metrics
         Metric metric = new Metric("CPU Usage", 50.0, System.currentTimeMillis());
         dataStorage.storeMetric(metric);
         
         // Handle alerts
         alerting.sendAlert("CPU usage is high!");
            
        
        
    }
    
}
