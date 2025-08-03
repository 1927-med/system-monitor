package main.java.com.systemmonitor;

import java.util.Objects;

public class Metric {
    private String name;
    private double value;
    private long timestamp;

    public Metric(String name, double value, long timestamp) {
        this.name = name;
        this.value = value;
        this.timestamp = timestamp;
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
    public String toString() {
        return "Metric{" +
                "name='" + name + '\'' +
                ", value=" + value +
                ", timestamp=" + timestamp +
                '}';
    }

     @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Metric metric = (Metric) o;
        return Double.compare(metric.value, value) == 0 &&
                timestamp == metric.timestamp &&
                Objects.equals(name, metric.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value, timestamp);
    }
    
}
