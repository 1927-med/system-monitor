package com.systemmonitor;

import javafx.application.Application;
import javafx.stage.Stage;

public class SystemMonitor extends Application {
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Initialize graphing system
        Graphing.initialize(primaryStage);
        
        // Start monitoring
        MonitoringService monitoringService = new MonitoringService();
        monitoringService.startMonitoring();
    }
}