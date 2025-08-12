package com.systemmonitor;

import java.util.Map;

public final class AlertManager {
    private final Map<MetricType, Threshold> thresholds;

    public AlertManager(Map<MetricType, Threshold> thresholds) {
        // Validate all metric types are present
        for (MetricType type : MetricType.values()) {
            if (!thresholds.containsKey(type)) {
                throw new IllegalArgumentException("Missing threshold for " + type);
            }
        }
        this.thresholds = Map.copyOf(thresholds); // Make immutable
    }

    public void checkMetrics(Map<MetricType, Double> currentMetrics) {
        currentMetrics.forEach((metric, value) -> {
            if (value > thresholds.get(metric).value()) {
                sendAlert(metric + " threshold exceeded: " + value + "%");
            }
        });
    }

    private void sendAlert(String message) {
        System.getLogger(AlertManager.class.getName())
              .log(System.Logger.Level.WARNING, message);
    }

    public enum MetricType { CPU, MEMORY, DISK, NETWORK }
}


