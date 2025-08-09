package com.systemmonitor;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javafx.application.Platform;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.NetworkIF;
import oshi.software.os.OSFileStore;

public class MonitoringService {
    // System monitoring components
    private final SystemInfo systemInfo = new SystemInfo();
    private final CentralProcessor processor = systemInfo.getHardware().getProcessor();
    private final GlobalMemory memory = systemInfo.getHardware().getMemory();
    private final List<NetworkIF> networkInterfaces = systemInfo.getHardware().getNetworkIFs();
    private NetworkIF primaryNetworkInterface;
    private long[] prevTicks;

    public MonitoringService() {
        // Initialize primary network interface (use first available)
        this.primaryNetworkInterface = !networkInterfaces.isEmpty() ? 
            networkInterfaces.get(0) : null;
    }

    public void startMonitoring() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        prevTicks = processor.getSystemCpuLoadTicks();
        
        executor.scheduleAtFixedRate(() -> {
            try {
                // Collect real system metrics
                double cpu = getCpuUsage();
                double memoryUsage = getMemoryUsage();
                double disk = getDiskUsage();
                double network = getNetworkUsage();
                
                // Update UI on JavaFX thread
                Platform.runLater(() -> {
                    Graphing.updateGraph("CPU Usage (%)", cpu);
                    Graphing.updateGraph("Memory Usage (%)", memoryUsage);
                    Graphing.updateGraph("Disk Usage (%)", disk);
                    Graphing.updateGraph("Network (KB/s)", network);
                    new Alerting().checkThresholds(cpu, memoryUsage, disk);
                });
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Monitoring interrupted: " + e.getMessage());
            }
        }, 0, 2, TimeUnit.SECONDS); // Update every 2 seconds
    }

    // Returns list of available network interface names
    public List<String> getNetworkInterfaceNames() {
        return networkInterfaces.stream()
            .map(NetworkIF::getName)
            .collect(Collectors.toList());
    }

    // Change active network interface
    public void setPrimaryNetworkInterface(int index) {
        if (index >= 0 && index < networkInterfaces.size()) {
            this.primaryNetworkInterface = networkInterfaces.get(index);
        }
    }

    private double getCpuUsage() {
        long[] ticks = processor.getSystemCpuLoadTicks();
        double cpuLoad = processor.getSystemCpuLoadBetweenTicks(prevTicks) * 100;
        prevTicks = ticks;
        return Math.round(cpuLoad * 10) / 10.0; // Round to 1 decimal place
    }

    private double getMemoryUsage() {
        return ((memory.getTotal() - memory.getAvailable()) * 100.0) / memory.getTotal();
    }

    private double getDiskUsage() {
        OSFileStore fileStore = systemInfo.getOperatingSystem().getFileSystem().getFileStores().get(0);
        return (fileStore.getTotalSpace() - fileStore.getFreeSpace()) * 100.0 / fileStore.getTotalSpace();
    }

    private double getNetworkUsage() throws InterruptedException {
        if (primaryNetworkInterface != null) {
            primaryNetworkInterface.updateAttributes();
            long startBytes = primaryNetworkInterface.getBytesRecv();
            TimeUnit.SECONDS.sleep(1);
            return (primaryNetworkInterface.getBytesRecv() - startBytes) / 1024.0; // KB/s
        }
        return 0;
    }
}