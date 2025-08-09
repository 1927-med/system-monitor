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

    public static void initialize(Stage stage) {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        chart = new LineChart<>(xAxis, yAxis);
        stage.setScene(new javafx.scene.Scene(chart, 800, 600));
    }

    public static void updateGraph(String metricName, double value) {
        Platform.runLater(() -> {
            XYChart.Series<Number, Number> series = seriesMap.computeIfAbsent(metricName, k -> {
                XYChart.Series<Number, Number> newSeries = new XYChart.Series<>();
                newSeries.setName(metricName);
                chart.getData().add(newSeries);
                return newSeries;
            });
            
            series.getData().add(new XYChart.Data<>(timeCounter++, value));
            
            // Limit to 30 data points
            if (series.getData().size() > 30) {
                series.getData().remove(0);
            }
        });
    }
}