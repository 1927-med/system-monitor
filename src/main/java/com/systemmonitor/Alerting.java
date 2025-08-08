package com.systemmonitor;

public class Alerting {
    public void sendAlert(String message) {
        // Logic to send an alert
        System.out.println("Alert sent: " + message);
        logAlert(message);
        notifyAdmin(message);
    }

    public void logAlert(String message) {
        // Logic to log the alert
        System.out.println("Alert logged: " + message);
    }

    public void notifyAdmin(String message) {
        // Logic to notify the admin
        System.out.println("Admin notified: " + message);
    }
    
}
