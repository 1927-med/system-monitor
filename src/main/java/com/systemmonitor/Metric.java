package com.systemmonitor;

public class Metric {
    private final String name;
    private final double value;
    private final long timestamp;

    public Metric(String name, double value, long timestamp) {
        this.name = name;
        this.value = value;
        this.timestamp = timestamp;
    }

    // Getters, toString, equals, hashCode...
    @Override
    public String toString() {
        return String.format("%s=%.2f @ %d", name, value, timestamp);
    }
    public String getName() {
        return name;
    }
    public double getValue() {
        return value;
    }
    public long getTimestamp() {
        return timestamp;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Metric) {
            Metric other = (Metric) obj;
            return name.equals(other.name) && value == other.value && timestamp == other.timestamp;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return name.hashCode() ^ Double.hashCode(value) ^ Long.hashCode(timestamp);
    }
}