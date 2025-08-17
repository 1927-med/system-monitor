package com.systemmonitor;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;

public class Notification {
    private final boolean systemTraySupported;
    
    public Notification() {
        this.systemTraySupported = SystemTray.isSupported();
        if (systemTraySupported && PlatformUtils.isLinux()) {
            // Linux may need additional setup for notifications
            System.setProperty("java.awt.headless", "false");
        }
    }

    public void sendNotification(String title, String message) {
        if (systemTraySupported) {
            try {
                SystemTray tray = SystemTray.getSystemTray();
                Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
                TrayIcon trayIcon = new TrayIcon(image, "System Monitor");
                trayIcon.setImageAutoSize(true);
                tray.add(trayIcon);
                trayIcon.displayMessage(title, message, MessageType.WARNING);
                tray.remove(trayIcon);
            } catch (AWTException e) {
                System.err.println("Failed to show notification: " + e.getMessage());
            }
        } else {
            // Fallback for systems without system tray
            System.out.printf("[ALERT] %s: %s%n", title, message);
        }
    }
}