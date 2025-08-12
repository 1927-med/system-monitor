package com.systemmonitor;

import java.util.List;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SystemMonitor extends Application {
    private MonitoringService monitor;
    private ComboBox<String> networkInterfaceComboBox;

    public static void main(String[] args) {
        // Configure MacOS-specific settings
        System.setProperty("glass.platform", "macosx");
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.verbose", "true"); // For debugging
        
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Initialize monitoring service and graphing
        monitor = new MonitoringService();
        Stage graphStage = new Stage();
        LineChartGraph.initialize(graphStage);
        
        // Set up network interface selection UI
        networkInterfaceComboBox = new ComboBox<>();
        Label interfaceLabel = new Label("Select Network Interface:");
        updateNetworkInterfaceList();
        
        networkInterfaceComboBox.setOnAction(event -> {
            int selectedIndex = networkInterfaceComboBox.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                monitor.setPrimaryNetworkInterface(selectedIndex);
            }
        });

        // Create UI layout
        VBox root = new VBox(10, interfaceLabel, networkInterfaceComboBox);
        root.setPadding(new Insets(15));
        
        // Configure control window
        Scene controlScene = new Scene(root, 300, 150);
        primaryStage.setScene(controlScene);
        primaryStage.setTitle("Monitor Controls");
        primaryStage.setOnCloseRequest(event -> shutdown());
        
        // Show both windows
        primaryStage.show();
        graphStage.show();
        
        // Start monitoring
        monitor.startMonitoring();
    }

    private void updateNetworkInterfaceList() {
        List<String> interfaces = monitor.getNetworkInterfaceNames();
        networkInterfaceComboBox.getItems().setAll(interfaces);
        if (!interfaces.isEmpty()) {
            networkInterfaceComboBox.getSelectionModel().selectFirst();
            monitor.setPrimaryNetworkInterface(0);
        }
    }

    private void shutdown() {
        if (monitor != null) {
            // This is just a place holder i will finish this thing later 
            System.exit(0);
        }
    }
}