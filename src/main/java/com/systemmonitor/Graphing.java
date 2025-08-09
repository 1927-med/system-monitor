package com.systemmonitor;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class Graphing extends Application {
    private double[] values;
    private String title;

    public void createGraph(String title, double[] values) {
        this.title = title;
        this.values = values;
        new Thread(this::startGraph).start();
    }

    private void startGraph() {
        Application.launch(Graphing.class);
    }

    @Override
    public void start(Stage stage) {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        for (int i = 0; i < values.length; i++) {
            series.getData().add(new XYChart.Data<>(i, values[i]));
        }
        
        chart.getData().add(series);
        stage.setScene(new Scene(chart, 800, 600));
        stage.setTitle(title);
        stage.show();
    }
}