package com.systemmonitor;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Platform;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class Graphing {
    private static LineChart<Number, Number> chart;
    private static final Map<String, XYChart.Series<Number, Number>> seriesMap = new HashMap<>();
    private static int timeCounter = 0;
    private static final int MAX_DATA_POINTS = 60;
    private static Stage primaryStage;

    public static void initialize(Stage stage) {
        primaryStage = stage;
        NumberAxis xAxis = new NumberAxis("Time (seconds)", 0, MAX_DATA_POINTS, 10);
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Usage (%)");
        
        chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle("Real-Time System Monitoring");
        chart.setAnimated(false);
        chart.setCreateSymbols(false);
        chart.setLegendVisible(true);
        
        primaryStage.setScene(new javafx.scene.Scene(chart, 1000, 600));
    }

    public static void updateGraph(String metricName, double value) {
        Platform.runLater(() -> {
            XYChart.Series<Number, Number> series = seriesMap.computeIfAbsent(metricName, k -> {
                XYChart.Series<Number, Number> newSeries = new XYChart.Series<>();
                newSeries.setName(metricName);
                chart.getData().add(newSeries);
                return newSeries;
            });
            
            series.getData().add(new XYChart.Data<>(timeCounter, value));
            
            if (series.getData().size() > MAX_DATA_POINTS) {
                series.getData().remove(0);
            }
            
            // Update time counter only for CPU metric to sync all graphs
            if ("CPU Usage (%)".equals(metricName)) {
                timeCounter++;
                
                // Auto-scroll X axis
                if (timeCounter > MAX_DATA_POINTS) {
                    NumberAxis xAxis = (NumberAxis) chart.getXAxis();
                    xAxis.setLowerBound(xAxis.getLowerBound() + 1);
                    xAxis.setUpperBound(xAxis.getUpperBound() + 1);
                }
            }
        });
    }
}