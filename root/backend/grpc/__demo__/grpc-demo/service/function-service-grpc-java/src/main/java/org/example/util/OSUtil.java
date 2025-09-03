package org.example.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class OSUtil {

    public static String hostname() {
        String hostname;
        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            hostname = "";
        }
        return hostname;
    }

    public static String platform() {
        String os = System.getProperty("os.name");
        String arch = System.getProperty("os.arch");
        return os + "/" + arch;
    }
}
