package xyz.icefery.ice.rpc.service.discovery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;

@Slf4j
public class ZooKeeperServiceDiscovery implements ServiceDiscovery {

    private final CuratorFramework zookeeper;

    public ZooKeeperServiceDiscovery(String address) {
        this.zookeeper = CuratorFrameworkFactory.builder().connectString(address).retryPolicy(new RetryNTimes(3, 1000)).build();
        this.zookeeper.start();
        log.info("success to connect to zookeeper {address={}}", address);
    }

    @Override
    public void register(String serviceName, String serviceAddress) {
        String path = "/ice/" + serviceName + "/" + serviceAddress;
        try {
            if (zookeeper.checkExists().forPath(path) != null) {
                unregister(serviceName, serviceAddress);
            }
            zookeeper.create().creatingParentContainersIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path);
        } catch (Exception e) {
            String message = String.format("failure to register service instance {serviceName=%s serviceAddress=%s}", serviceName, serviceAddress);
            log.error(message, e);
            throw new RuntimeException(message);
        }
    }

    @Override
    public void unregister(String serviceName, String serviceAddress) {
        String path = "/ice/" + serviceName + "/" + serviceAddress;
        try {
            zookeeper.delete().deletingChildrenIfNeeded().forPath(path);
        } catch (Exception e) {
            String message = String.format("failure to unregister service instance {serviceName=%s serviceAddress=%s}", serviceName, serviceAddress);
            log.error(message, e);
            throw new RuntimeException(message);
        }
    }

    @Override
    public Map<String, List<String>> instances() {
        Map<String, List<String>> result = new HashMap<>();
        String path = "/ice";
        try {
            List<String> serviceList = zookeeper.getChildren().forPath(path);
            for (String serviceName : serviceList) {
                List<String> addressList = zookeeper.getChildren().forPath(path + "/" + serviceName);
                result.put(serviceName, addressList);
            }
        } catch (Exception e) {
            String message = "failure to get instances";
            log.error(message, e);
            throw new RuntimeException(message);
        }
        return result;
    }
}
