package com.systemmonitor;

public interface DataStorage {
    void storeMetric(Metric metric);
    String retrieveData();
    void clearData();
}