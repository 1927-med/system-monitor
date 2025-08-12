package com.systemmonitor;

import java.util.Map;

public final class AlertManager {
    private final Map<MetricType, Threshold> thresholds;

    public AlertManager(Map<MetricType, Threshold> thresholds) {
        this.thresholds = thresholds;
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

    public enum MetricType { CPU, MEMORY, DISK }
}

public record Threshold(int value) {
    public Threshold {
        if (value < 0 || value > 100) {
            throw new IllegalArgumentException("Threshold must be 0-100");
        }
    }
}