package com.systemmonitor;

import java.util.List;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This class is the main entry point for the System Monitor application.
 * It sets up the user interface and starts the monitoring service.
 * It allows users to select a network interface for monitoring
 * and displays real-time system metrics in a separate window.
 * * The application is designed to run on MacOS with specific configurations
 * for the JavaFX platform.
 * * The main features include:
 * * Tracking CPU usage, memory usage, disk space usage, and network connectivity.
 * * Alerts users to potential problems via email or desktop notifications.
 * * Configurable thresholds and alerting mechanisms
 */

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
/**
 * This method is called when the application starts.
 * It sets up the user interface and starts the monitoring service.
 */
    @Override
    public void start(Stage primaryStage) {
        // Initialize monitoring service and graphing
        monitor = new MonitoringService();
        Stage graphStage = new Stage();
        Graphing.initialize(graphStage);
        
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
/**
 * This method updates the list of network interfaces available for monitoring.
 */
    private void updateNetworkInterfaceList() {
        List<String> interfaces = monitor.getNetworkInterfaceNames();
        networkInterfaceComboBox.getItems().setAll(interfaces);
        if (!interfaces.isEmpty()) {
            networkInterfaceComboBox.getSelectionModel().selectFirst();
            monitor.setPrimaryNetworkInterface(0);
        }
    }
/**
 * This method is called when the application is closed.
 * It stops the monitoring service and exits the application.
 * although this needs work to be implemented, for now it doesnt have much functionality as users 
 * can simply close the gui window and the program will exit
 */

 //TODO implement this and dont forget this bro 
    private void shutdown() {
        if (monitor != null) {
            // This is just a place holder i will finish this thing later 
            System.exit(0);
        }
    }
}