package com.systemmonitor;

public class Alerting {
    public void sendAlert(String message) {
        System.out.println("ALERT: " + message);
        logAlert(message);
        notifyAdmin(message);
    }

    private void logAlert(String message) {
        System.out.println("LOG: " + message);
    }

    private void notifyAdmin(String message) {
        System.out.println("ADMIN NOTIFICATION: " + message);
    }
}