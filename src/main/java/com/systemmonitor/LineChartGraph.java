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
    private final LineChart<Number, Number> chart;
    private final Map<String, XYChart.Series<Number, Number>> seriesMap = new HashMap<>();
    private int timeCounter = 0;

    public LineChartGraph(Stage stage) {
        NumberAxis xAxis = new NumberAxis("Time (seconds)", 0, 60, 10);
        NumberAxis yAxis = new NumberAxis("Usage (%)", timeCounter, timeCounter, timeCounter);// TODO 
        //this yAxis should show percentage values for now the compiler is not allowing me to do that
        //and i basically used the timeCounter value as the max value, its frustrating but no option for now 
        chart = new LineChart<>(xAxis, yAxis);
        chart.setAnimated(false);
        stage.setScene(new Scene(chart, 1000, 600));
    }

    public void update(String metricName, double value) {
        Platform.runLater(() -> {
            XYChart.Series<Number, Number> series = seriesMap.computeIfAbsent(metricName,
                k -> {
                    XYChart.Series<Number, Number> newSeries = new XYChart.Series<>();
                    newSeries.setName(metricName);
                    chart.getData().add(newSeries);
                    return newSeries;
                });
            series.getData().add(new XYChart.Data<>(timeCounter, value));
            
            if ("CPU".equals(metricName)) timeCounter++;
        });
    }
}