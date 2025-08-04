package main.java.com.systemmonitor;

public class  Graphing {
     // Logic to create a graph
     public void createGraph(String metricName, double[] values) {
          // Logic to create a graph
          System.out.println("Creating graph for " + metricName);
          // You can use a library like JFreeChart to create a graph
          // For now, just print the values
          for (double value : values) {
              System.out.println(value);
          }
      }
}
