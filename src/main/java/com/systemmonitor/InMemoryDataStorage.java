package com.systemmonitor;

import java.util.ArrayList;
import java.util.List;

public class InMemoryDataStorage implements DataStorage {
    private final List<Metric> metrics = new ArrayList<>();

    @Override
    public void storeMetric(Metric metric) {
        metrics.add(metric);
        System.out.println("Stored metric: " + metric);
    }

    @Override
    public String retrieveData() {
        return metrics.toString();
    }

    @Override
    public void clearData() {
        metrics.clear();
    }
}