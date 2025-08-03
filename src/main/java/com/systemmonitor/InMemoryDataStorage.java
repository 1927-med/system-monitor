package main.java.com.systemmonitor;

import java.util.ArrayList;

public class InMemoryDataStorage implements DataStorage {
    private ArrayList<Metric> metrics = new ArrayList<>();
    @Override
    public void storeMetric(Metric metric) {
        metrics.add(metric);
    }

    @Override
    public String retrieveData() {
        return metrics.toString();
    }

    @Override
    public void clearData() {
        metrics.clear();
    }
    public class Alerting {
        public void sendAlert(String message) {
            try {
                // Logic to send an alert
                System.out.println("Alert sent: " + message);
            } catch (Exception e) {
                System.err.println("Error sending alert: " + e.getMessage());
            }
        }
}