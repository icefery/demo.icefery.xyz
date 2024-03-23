package org.example.module.greeting;

import xyz.icefery.ice.rpc.server.RPCServer;
import xyz.icefery.ice.rpc.service.discovery.ZooKeeperServiceDiscovery;
import java.util.Map;

public class GreetingApp {
    public static void main(String[] args) throws Exception {
        String serviceName = "greeting-service";
        Integer port = 9999;
        ZooKeeperServiceDiscovery serviceDiscovery = new ZooKeeperServiceDiscovery("192.192.192.6:2181");
        Map<String, ?> apiMap = Map.of(
            HelloService.class.getName(), new HelloService(),
            HiService.class.getName(), new HiService()
        );
        new RPCServer(serviceName, port, serviceDiscovery, apiMap).start();
    }
}
