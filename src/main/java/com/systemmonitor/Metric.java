package com.systemmonitor;

public record Metric(String name, double value, long timestamp) {
    public Metric {
        if (value < 0) throw new IllegalArgumentException("Value cannot be negative");
    }
}