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

    public String retrieveData(String key) {
        // Logic to retrieve data
        // For example, return a string representation of the metric
        for (Metric metric : metrics) {
            if (metric.getName().equals(key)) {
                return metric.toString();
            }
        }
        return null;
    }

    public void clearData() {
        // Logic to clear stored data
    }
    
}
