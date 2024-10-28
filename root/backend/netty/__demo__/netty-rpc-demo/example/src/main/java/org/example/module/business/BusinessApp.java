package org.example.module.business;

import xyz.icefery.ice.rpc.client.RPCClient;
import xyz.icefery.ice.rpc.common.LoadBalancer;
import xyz.icefery.ice.rpc.common.RandomLoadBalancer;
import xyz.icefery.ice.rpc.service.discovery.ServiceDiscovery;
import xyz.icefery.ice.rpc.service.discovery.ZooKeeperServiceDiscovery;

public class BusinessApp {

    public static void main(String[] args) {
        ServiceDiscovery serviceDiscovery = new ZooKeeperServiceDiscovery("192.192.192.6:2181");
        LoadBalancer<String> loadBalancer = new RandomLoadBalancer<>();
        RPCClient client = new RPCClient(serviceDiscovery, loadBalancer);

        Object result;
        BusinessService businessService = client.reference(BusinessService.class);

        result = businessService.sayHello("icefery");
        System.out.println(result);

        result = businessService.sayHi("icefery");
        System.out.println(result);

        result = businessService.fibonacci(10);
        System.out.println(result);
    }
}
