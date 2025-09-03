package org.example.module.math;

import java.util.Map;
import xyz.icefery.ice.rpc.server.RPCServer;
import xyz.icefery.ice.rpc.service.discovery.ZooKeeperServiceDiscovery;

public class MathApp {

    public static void main(String[] args) throws Exception {
        String serviceName = "math-service";
        Integer port = 8888;
        ZooKeeperServiceDiscovery serviceDiscovery = new ZooKeeperServiceDiscovery("192.192.192.6:2181");
        Map<String, ?> apiMap = Map.of(MathService.class.getName(), new MathService());

        new RPCServer(serviceName, port, serviceDiscovery, apiMap).start();
    }
}
