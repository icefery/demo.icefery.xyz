## 快速开始

#### 服务端

```java
public class MathService {
    public Integer fibonacci(Integer n) {
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        } else {
            return fibonacci(n - 1) + fibonacci(n - 2);
        }
    }
}
```

```java
public class MathApp {
    public static void main(String[] args) throws Exception {
        String serviceName = "math-service";
        Integer port = 8888;
        ZooKeeperServiceDiscovery serviceDiscovery = new ZooKeeperServiceDiscovery("192.192.192.6:2181");
        Map<String, ?> apiMap = Map.of(
            MathService.class.getName(), new MathService()
        );
        new RPCServer(serviceName, port, serviceDiscovery, apiMap).start();
    }
}
```

#### 客户端

```java
public interface BusinessService {
    @RPCReference(serviceName = "math-service", className = "org.example.module.math.MathService", methodName = "fibonacci")
    Integer fibonacci(Integer n);
}
```

```java
public class BusinessApp {
    public static void main(String[] args) {
        ServiceDiscovery serviceDiscovery = new ZooKeeperServiceDiscovery("192.192.192.6:2181");
        LoadBalancer<String> loadBalancer = new RandomLoadBalancer<>();
        RPCClient client = new RPCClient(serviceDiscovery, loadBalancer);

        BusinessService businessService = client.reference(BusinessService.class);

        Integer result = businessService.fibonacci(10);
        System.out.println(result);
    }
}
```
