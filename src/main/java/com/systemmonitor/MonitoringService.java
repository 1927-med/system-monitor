package com.systemmonitor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.NetworkIF;
import oshi.software.os.OSFileStore;

public class MonitoringService {
    private final AlertManager alertManager;
    private final LineChartGraph graph;
    private final ConsoleLogger logger; 
    private final SystemInfo systemInfo = new SystemInfo();
    private final CentralProcessor processor = systemInfo.getHardware().getProcessor();
    private final GlobalMemory memory = systemInfo.getHardware().getMemory();
    private final List<NetworkIF> networkInterfaces = systemInfo.getHardware().getNetworkIFs();
    private NetworkIF primaryNetworkInterface;
    private long[] prevTicks;
    private ScheduledExecutorService executor;

    public MonitoringService(AlertManager alertManager, LineChartGraph graph, ConsoleLogger logger) {
        this.alertManager = alertManager;
        this.graph = graph;
        this.logger = logger; // Add this field: private final ConsoleLogger logger;
        if (!networkInterfaces.isEmpty()) {
            this.primaryNetworkInterface = networkInterfaces.get(0);
        }
    }

    public void startMonitoring() {
        prevTicks = processor.getSystemCpuLoadTicks();
        executor = Executors.newSingleThreadScheduledExecutor();
        
        executor.scheduleAtFixedRate(() -> {
            try {
                Map<AlertManager.MetricType, Double> metrics = Map.of(
                    AlertManager.MetricType.CPU, getCpuUsage(),
                    AlertManager.MetricType.MEMORY, getMemoryUsage(),
                    AlertManager.MetricType.DISK, getDiskUsage(),
                    AlertManager.MetricType.NETWORK, getNetworkUsage()
                );
                
                metrics.forEach((type, value) -> {
                    graph.update(type.name(), value);
                });
                
                alertManager.checkMetrics(metrics);
            } catch (Exception e) {
                System.err.println("Monitoring error: " + e.getMessage());
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    public List<String> getNetworkInterfaceNames() {
        return networkInterfaces.stream()
            .map(NetworkIF::getName)
            .toList();
    }

    public void setPrimaryNetworkInterface(int index) {
        if (index >= 0 && index < networkInterfaces.size()) {
            this.primaryNetworkInterface = networkInterfaces.get(index);
        }
    }

    private double getCpuUsage() {
        long[] ticks = processor.getSystemCpuLoadTicks();
        double cpuLoad = processor.getSystemCpuLoadBetweenTicks(prevTicks) * 100;
        prevTicks = ticks;
        return Math.round(cpuLoad * 10) / 10.0;
    }

    private double getMemoryUsage() {
        return ((memory.getTotal() - memory.getAvailable()) * 100.0) / memory.getTotal();
    }

    private double getDiskUsage() {
        OSFileStore fileStore = systemInfo.getOperatingSystem().getFileSystem().getFileStores().get(0);
        return (fileStore.getTotalSpace() - fileStore.getFreeSpace()) * 100.0 / fileStore.getTotalSpace();
    }

    private double getNetworkUsage() {
        try {
            if (primaryNetworkInterface != null) {
                primaryNetworkInterface.updateAttributes();
                long startBytes = primaryNetworkInterface.getBytesRecv();
                TimeUnit.SECONDS.sleep(1);
                primaryNetworkInterface.updateAttributes();
                return (primaryNetworkInterface.getBytesRecv() - startBytes) / 1024.0;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return 0;
    }

    public void shutdown() {
        if (executor != null) {
            executor.shutdownNow();
        }
    }
}