package com.systemmonitor;

import java.util.List;
import java.util.Map;

import com.systemmonitor.AlertManager.MetricType;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SystemMonitor extends Application {
    private MonitoringService monitor;
    private ComboBox<String> networkInterfaceComboBox;
    private LineChartGraph lineChart;
   

    public static void main(String[] args) {
        configureMacOSSettings();
        launch(args);
    }

    private static void configureMacOSSettings() {
        System.setProperty("glass.platform", "macosx");
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.verbose", "true");
    }

    @Override
    public void start(Stage primaryStage) {
        initializeComponents(primaryStage);
        setupUI(primaryStage);
        startMonitoring();
    }

    private void initializeComponents(Stage primaryStage) {
       // Initialize with ALL required thresholds
       ConfigManager config = new ConfigManager();
    
       AlertManager alertManager = new AlertManager(Map.of(
           AlertManager.MetricType.CPU, 
               new Threshold(Integer.parseInt(config.getProperty("cpu.threshold"))),
           AlertManager.MetricType.MEMORY, 
               new Threshold(Integer.parseInt(config.getProperty("memory.threshold"))),
           AlertManager.MetricType.DISK, 
               new Threshold(Integer.parseInt(config.getProperty("disk.threshold"))),
           AlertManager.MetricType.NETWORK, 
               new Threshold(Integer.parseInt(config.getProperty("network.threshold")))
       ));
   
       Stage graphStage = new Stage();
       graphStage.setTitle("System Metrics - " + PlatformUtils.getPlatformName());
       this.lineChart = new LineChartGraph(graphStage);
       
       this.monitor = new MonitoringService(
           alertManager,
           lineChart,
           new ConsoleLogger(),
           new Notification(),
           config
       );
    }

    private void setupUI(Stage primaryStage) {
        networkInterfaceComboBox = new ComboBox<>();
        Label interfaceLabel = new Label("Select Network Interface:");
        updateNetworkInterfaceList();
        
        networkInterfaceComboBox.setOnAction(event -> {
            int selectedIndex = networkInterfaceComboBox.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                monitor.setPrimaryNetworkInterface(selectedIndex);
            }
        });

        VBox root = new VBox(10, interfaceLabel, networkInterfaceComboBox);
        root.setPadding(new Insets(15));
        
        Scene controlScene = new Scene(root, 300, 150);
        primaryStage.setScene(controlScene);
        primaryStage.setTitle("Monitor Controls");
        primaryStage.setOnCloseRequest(event -> shutdown());
        primaryStage.show();
    }

    private void updateNetworkInterfaceList() {
        List<String> interfaces = monitor.getNetworkInterfaceNames();
        networkInterfaceComboBox.getItems().setAll(interfaces);
        if (!interfaces.isEmpty()) {
            networkInterfaceComboBox.getSelectionModel().selectFirst();
            monitor.setPrimaryNetworkInterface(0);
        }
    }

    private void startMonitoring() {
        monitor.startMonitoring();
    }

    private void shutdown() {
        if (monitor != null) {
            monitor.shutdown();
        }
        Platform.exit();
    }
}