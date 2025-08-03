package main.java.com.systemmonitor;

public class Notification {
    private String name;
    private String email;
    
    public Notification(String name, String email) {
        this.name = name;
        this.email = email;
    }
    
    public void notifyAdmin(String message) {
        // Logic to notify the admin
        System.out.println("Admin notified: " + message);
    }
    
    public void sendNotification(String message) {
        // Logic to send a notification
        System.out.println("Notification sent: " + message);
    }
    
}
