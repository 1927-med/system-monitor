package com.systemmonitor;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;


public final class LineChartGraph {
    private static final int MAX_DATA_POINTS = 60; // assumption here is i took 60 sec for it to be displayed
        private final LineChart<Number, Number> chart;
        private final Map<String, XYChart.Series<Number, Number>> seriesMap = new HashMap<>();
        private int timeCounter = 0;
    
        public LineChartGraph(Stage stage) {
            NumberAxis xAxis = new NumberAxis("Time (seconds)", 0, 60, 10);
            NumberAxis yAxis = new NumberAxis("Usage (%)", 0, 100, 10); 
            chart = new LineChart<>(xAxis, yAxis);
            chart.setTitle("System Metrics");
            chart.setAnimated(false);
            chart.setCreateSymbols(false);
            
            Scene scene = new Scene(chart, 1000, 600);
            stage.setScene(scene);
            stage.show(); // Ensure window is shown
        }
    
        public void update(String metricName, double value) {
            System.out.printf("Chart Update - %s: %.2f at %d%n", metricName, value, timeCounter);
            // Ensure the metric name is valid
            Platform.runLater(() -> {
                XYChart.Series<Number, Number> series = seriesMap.computeIfAbsent(metricName,
                    k -> {
                        XYChart.Series<Number, Number> newSeries = new XYChart.Series<>();
                        newSeries.setName(metricName);
                        chart.getData().add(newSeries);
                        return newSeries;
                    });
                
                series.getData().add(new XYChart.Data<>(timeCounter, value));
                
                // Remove oldest point if we exceed maximum
                if (series.getData().size() > MAX_DATA_POINTS) {
                    series.getData().remove(0);
                }
                if (metricName.startsWith("CPU")) { // More flexible check
                    timeCounter++;
                // Auto-scroll X axis
                if (timeCounter > MAX_DATA_POINTS) {
                    NumberAxis xAxis = (NumberAxis) chart.getXAxis();
                    xAxis.setLowerBound(xAxis.getLowerBound() + 1);
                    xAxis.setUpperBound(xAxis.getUpperBound() + 1);
                }
            
           // Only increment time counter for one metric (to avoid multiple increments)
           if ("CPU Usage (%)".equals(metricName)) {
            timeCounter++;
        }
            
        }
    });
}
}
    
    
