package com.systemmonitor;

public class HistoricalData {
    public void storeMetric(String metricName, double value) {
        System.out.printf("Storing historical metric: %s = %.2f\n", metricName, value);
    }
}