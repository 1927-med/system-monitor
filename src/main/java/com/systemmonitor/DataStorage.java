package main.java.com.systemmonitor;

import java.util.ArrayList;

public interface DataStorage {
    void storeMetric(Metric metric);
    String retrieveData();
    void clearData();
    
}
