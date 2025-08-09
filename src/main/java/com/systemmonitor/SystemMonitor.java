package com.systemmonitor;

import javafx.application.Application;
import javafx.stage.Stage;

public class SystemMonitor extends Application {
    public static void main(String[] args) {
        // Workaround for MacOS
        System.setProperty("glass.platform", "macosx");
        System.setProperty("prism.order", "sw");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Graphing.initialize(primaryStage);
        new MonitoringService().startMonitoring();
        primaryStage.setTitle("System Monitor");
        primaryStage.show();
    }
}