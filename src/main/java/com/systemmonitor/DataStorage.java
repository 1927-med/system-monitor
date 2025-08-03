package main.java.com.systemmonitor;

import java.util.ArrayList;

public class DataStorage {
    // This class will handle data storage for the system monitor.
    // It can be implemented to store data in memory, a database, or any other storage solution.
    private ArrayList<Metric> metrics = new ArrayList<>();

    public void storeMetric( Metric metric) {
        // Logic to save data
        // For example, store the metric in memory
        metrics.add(metric);
    }

    public String retrieveData() {
        // Logic to retrieve data
        // For example, return a string representation of the metric

        return metrics.toString();
    }

    public void clearData() { 
        // Logic to clear stored data
        // For example, remove all metrics with a specific name
        metrics.clear();
        
       
    }
    
}
