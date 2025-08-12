package com.systemmonitor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.systemmonitor.AlertManager.MetricType;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.NetworkIF;
import oshi.software.os.OSFileStore;

public class MonitoringService {
    private final AlertManager alertManager;
    private final LineChartGraph graph;
    private final SystemInfo systemInfo = new SystemInfo();

    public MonitoringService(AlertManager alertManager, LineChartGraph graph) {
        this.alertManager = alertManager;
        this.graph = graph;
    }

    public void startMonitoring() {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            Map<MetricType, Double> metrics = Map.of(
                MetricType.CPU, getCpuUsage(),
                MetricType.MEMORY, getMemoryUsage()
            );
            
            metrics.forEach((type, value) -> {
                graph.update(type.name(), value);
            });
            
            alertManager.checkMetrics(metrics);
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

    private double getNetworkUsage() throws InterruptedException {
        if (primaryNetworkInterface != null) {
            primaryNetworkInterface.updateAttributes();
            long startBytes = primaryNetworkInterface.getBytesRecv();
            TimeUnit.SECONDS.sleep(1);
            return (primaryNetworkInterface.getBytesRecv() - startBytes) / 1024.0;
        }
        return 0;
    }

    public void shutdown() {
        if (executor != null) {
            executor.shutdownNow();
        }
    }
}