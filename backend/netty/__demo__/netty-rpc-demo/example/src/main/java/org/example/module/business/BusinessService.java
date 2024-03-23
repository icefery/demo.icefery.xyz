package org.example.module.business;

import xyz.icefery.ice.rpc.client.RPCReference;

public interface BusinessService {
    @RPCReference(serviceName = "greeting-service", className = "org.example.module.greeting.HelloService", methodName = "sayHello")
    String sayHello(String name);

    @RPCReference(serviceName = "greeting-service", className = "org.example.module.greeting.HiService", methodName = "sayHi")
    String sayHi(String name);

    @RPCReference(serviceName = "math-service", className = "org.example.module.math.MathService", methodName = "fibonacci")
    Integer fibonacci(Integer n);
}
