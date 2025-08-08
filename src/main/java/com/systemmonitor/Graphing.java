package com.systemmonitor;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class Graphing extends Application {
    private double[] values;

    public void createGraph(String title, double[] values) {
        this.values = values;
        // Run on JavaFX Application Thread
        new Thread(() -> Application.launch(Graphing.class)).start();
    }

    @Override
    public void start(Stage primaryStage) {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("System Metrics");

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Values");

        for (int i = 0; i < values.length; i++) {
            series.getData().add(new XYChart.Data<>(i, values[i]));
        }

        lineChart.getData().add(series);
        lineChart.setCreateSymbols(false); // Cleaner look without data point markers

        Scene scene = new Scene(lineChart, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}