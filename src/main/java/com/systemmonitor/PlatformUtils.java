package com.systemmonitor;

public class PlatformUtils {
    private static final String OS = System.getProperty("os.name").toLowerCase();

    public static boolean isWindows() {
        return OS.contains("win");
    }

    public static boolean isMac() {
        return OS.contains("mac");
    }

    public static boolean isLinux() {
        return OS.contains("linux") || OS.contains("unix");
    }

    public static String getPlatformName() {
        if (isWindows()) return "Windows";
        if (isMac()) return "macOS";
        if (isLinux()) return "Linux";
        return "Unknown";
    }
    
}
