package main.java.com.systemmonitor;

import java.util.ArrayList;

public class DataStorage {
    // This class will handle data storage for the system monitor.
    // It can be implemented to store data in memory, a database, or any other storage solution.

    public void storeMetric(Metric metric) {
        // Logic to save data
        ArrayList<Metric> metrics = new ArrayList<>();
        metrics.add(metric);
    }

    public String retrieveData(Metric metric) {
        // Logic to retrieve data
        // For example, return a string representation of the metric
        ArrayList<Metric> metrics = new ArrayList<>();
        return metrics.toString();
    }

    public void clearData(Metric metric) { 
        // Logic to clear stored data
        // For example, remove all metrics with a specific name
        
        ArrayList<Metric> metrics = new ArrayList<>();
        metrics.remove(metric);
    }
    
}
