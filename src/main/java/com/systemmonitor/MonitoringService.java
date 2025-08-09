package com.systemmonitor;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.software.os.OSFileStore;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MonitoringService {
    private final SystemInfo systemInfo = new SystemInfo();
    private final CentralProcessor processor = systemInfo.getHardware().getProcessor();
    private final GlobalMemory memory = systemInfo.getHardware().getMemory();
    private long[] prevTicks;

    public void startMonitoring() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        prevTicks = processor.getSystemCpuLoadTicks();
        
        executor.scheduleAtFixedRate(() -> {
            // Get real metrics
            double cpu = getCpuUsage();
            double memoryUsage = getMemoryUsage();
            double disk = getDiskUsage();
            
            // Update UI
            Platform.runLater(() -> {
                Graphing.updateGraph("CPU", cpu);
                Graphing.updateGraph("Memory", memoryUsage);
                new Alerting().checkThresholds(cpu, memoryUsage, disk);
            });
        }, 0, 2, TimeUnit.SECONDS); // Update every 2 seconds
    }

    private double getCpuUsage() {
        long[] ticks = processor.getSystemCpuLoadTicks();
        double cpuLoad = processor.getSystemCpuLoadBetweenTicks(prevTicks) * 100;
        prevTicks = ticks;
        return Math.round(cpuLoad * 10) / 10.0; // Round to 1 decimal
    }

    private double getMemoryUsage() {
        return ((memory.getTotal() - memory.getAvailable()) * 100.0) / memory.getTotal();
    }

    private double getDiskUsage() {
        OSFileStore fileStore = systemInfo.getOperatingSystem().getFileSystem().getFileStores().get(0);
        return (fileStore.getTotalSpace() - fileStore.getFreeSpace()) * 100.0 / fileStore.getTotalSpace();
    }
}