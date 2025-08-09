package com.systemmonitor;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class Graphing {
    private static Stage primaryStage;
    private static LineChart<Number, Number> chart;
    
    public static void initialize(Stage stage) {
        primaryStage = stage;
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        chart = new LineChart<>(xAxis, yAxis);
        primaryStage.setScene(new Scene(chart, 800, 600));
    }
    
    public static void createGraph(String title, double[] values) {
        Platform.runLater(() -> {
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            series.setName(title);
            
            for (int i = 0; i < values.length; i++) {
                series.getData().add(new XYChart.Data<>(i, values[i]));
            }
            
            chart.getData().add(series);
            primaryStage.show();
        });
    }
}