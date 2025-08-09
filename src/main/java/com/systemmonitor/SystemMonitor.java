package com.systemmonitor;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class SystemMonitor extends Application {
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Initialize with sample data
        double[] cpuData = {20, 45, 30, 60};
        double[] memoryData = {50, 65, 40, 70};
        
        // Setup graphs
        Graphing.initialize(primaryStage);
        Graphing.createGraph("CPU Usage", cpuData);
        Graphing.createGraph("Memory Usage", memoryData);
        
        // Start monitoring service
        new MonitoringService().startMonitoring();
        
        // Ensure application exits properly
        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
    }


    @Override
    public void stop() {
        // Cleanup resources if needed
        System.out.println("System Monitor application is closing.");
    }
}