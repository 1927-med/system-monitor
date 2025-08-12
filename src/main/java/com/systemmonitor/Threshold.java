package com.systemmonitor;

public record Threshold(int value) {
    public Threshold {
        if (value < 0 || value > 100) {
            throw new IllegalArgumentException("Threshold must be 0-100");
        }
    }

   
}
