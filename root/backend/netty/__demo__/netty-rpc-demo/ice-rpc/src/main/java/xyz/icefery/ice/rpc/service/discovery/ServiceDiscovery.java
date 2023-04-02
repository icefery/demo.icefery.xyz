package xyz.icefery.ice.rpc.service.discovery;

import java.util.List;
import java.util.Map;

public interface ServiceDiscovery {
    void register(String serviceName, String serviceAddress);

    void unregister(String serviceName, String serviceAddress);

    Map<String, List<String>> instances();
}
