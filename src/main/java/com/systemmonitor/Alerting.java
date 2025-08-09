package com.systemmonitor;

public class Alerting {
    private static final double CPU_THRESHOLD = 90;
    private static final double MEMORY_THRESHOLD = 85;
    private static final double DISK_THRESHOLD = 95;

    public void checkThresholds(double cpu, double memory, double disk) {
        if (cpu > 90) {
            sendAlert(String.format("CPU threshold exceeded: %.1f%%", cpu));
        }
        if (memory > 85) {
            sendAlert(String.format("Memory threshold exceeded: %.1f%%", memory));
        }
        if (disk > 95) {
            sendAlert(String.format("Disk threshold exceeded: %.1f%%", disk));
        }
    }

    private void sendAlert(String message) {
        System.out.println("[ALERT] " + message);
    }
}