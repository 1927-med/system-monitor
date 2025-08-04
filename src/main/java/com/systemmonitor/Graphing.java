package main.java.com.systemmonitor;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class  Graphing extends Application {
     private double[] values;

    public void createGraph(String title, double[] values) {
        this.values = values;
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Graph");

        XYChart.Series series = new XYChart.Series();
        series.setName("Values");

        for (int i = 0; i < values.length; i++) {
            series.getData().add(new XYChart.Data(i, values[i]));
        }

        lineChart.getData().add(series);

        Scene scene = new Scene(lineChart, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
