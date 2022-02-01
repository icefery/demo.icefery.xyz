## 一、说明

### 版本

SpringBoot 与 SpringCloud 版本适配关系：[https://start.spring.io/actuator/info](https://start.spring.io/actuator/info)

### 组件

|                |                                      |
| -------------- | ------------------------------------ |
| 服务注册与发现 | netflix-eureka                       |
| 服务调用       | RestTemplate、spring-cloud-openfeign |
| 负载均衡       | netflix-ribbon                       |
| 服务降级与熔断 | netflix-hystrix                      |
| 网关           | netflix-zuul                         |
| 配置中心       | spring-cloud-config                  |
| 消息总线       | spring-cloud-bus、rabbitmq           |

<br>

## 二、服务注册与发现

### 注册中心 Eureka

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```

```yaml
server:
  port: 8761

spring:
  application:
    name: EUREKA-SERVER

eureka:
  instance:
    hostname: localhost
  client:
    # 注册中心不需要检索服务
    fetch-registry: false
    # 注册中心不需要提供服务
    register-with-eureka: false
```

### Eureka 集群配置

> 由于 `eureka.instance.hostname` 要唯一，所以单机伪集群模式可以利用 hosts 解析欺骗 Eureka。

```properties
127.0.0.1 eureka8761
127.0.0.1 eureka8762
127.0.0.1 eureka8763
```

> 设置程序参数或环境变量更易于在容器中部署。程序参数比环境变量的优先级更高。

```properties
# eureka8761
--server.port=8761
--eureka.instance.hostname=eureka8761
--eureka.client.service-url.defaultZone=http://eureka8762:8762/eureka/,http://eureka8763:8763/eureka/

# eureka8762
--server.port=8762
--eureka.instance.hostname=eureka8762
--eureka.client.service-url.defaultZone=http://eureka8761:8761/eureka/,http://eureka8763:8763/eureka/

#eureka8763
--server.port=8763
--eureka.instance.hostname=eureka8763
--eureka.client.service-url.defaultZone=http://eureka8761:8761/eureka/,http://eureka8762:8762/eureka/
```

### 客户端

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

```java
@EnableDiscoveryClient
@SpringBootApplication
public class WareApplication {
    public static void main(String[] args) {
        SpringApplication.run(WareApplication.class, args);
    }
}
```

<br>

## 三、服务调用、负载均衡、降级熔断

### RestTemplate 调用

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
</dependency>
```

```properties
# =================== Ribbon ===================
ribbon.http.client.enabled=true
# 请求处理时间
# org.springframework.cloud.netflix.ribbon.RibbonClientConfiguration.DEFAULT_READ_TIMEOUT=1000
ribbon.ReadTimeout=300
# 负载均衡策略
MICROSERVICE-WARE.ribbon.NFLoadBalancerRuleClassName=com.netflix.loadbalancer.RandomRule

# =================== Hystrix ===================
# 降级超时时间
# com.netflix.hystrix.HystrixCommandProperties.default_executionTimeoutInMilliseconds=1000
hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds=2000
# 断路最小请求数量
# com.netflix.hystrix.HystrixCommandProperties.default_circuitBreakerRequestVolumeThreshold=20
hystrix.command.default.circuitBreaker.requestVolumeThreshold=5
# 断路时间窗口
# com.netflix.hystrix.HystrixCommandProperties.default_circuitBreakerSleepWindowInMilliseconds=5000
hystrix.command.default.circuitBreaker.sleepWindowInMilliseconds=3000
# 断路失败率阈值
# com.netflix.hystrix.HystrixCommandProperties.default_circuitBreakerErrorThresholdPercentage=50
hystrix.command.default.circuitBreaker.errorThresholdPercentage=40
```

```java
@Configuration
public class BeanConfig {
    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
```

```java
@EnableHystrix
@EnableDiscoveryClient
@SpringBootApplication
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
```

```java
@HystrixCommand(fallbackMethod = "wareFallback", commandProperties = {
    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
})
@GetMapping("/call_ware/sleep/{milliseconds}")
public R<Object> callWare(@PathVariable Long milliseconds) {
    R ware = restTemplate.getForObject("http://MICROSERVICE-WARE/sleep/{milliseconds}", R.class, milliseconds);

    Map<String, Object> map = new HashMap<>();
    map.put("applicationName", applicationName);
    map.put("port", port);
    map.put("ware", ware);

    return R.success(map);
}
```

```java
private R<Object> wareFallback(Long milliseconds) {
    R<Object> ware = R.failure(503, "[MICROSERVICE-WARE] 暂时不可用");

    Map<String, Object> map = new HashMap<>();
    map.put("applicationName", applicationName);
    map.put("port", port);
    map.put("ware", ware);

    return R.success(map);
}
```

### openfeign 调用

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

```properties
# ribbon 和 hystrix 的配置对基于它们的 openfeign 同样有效
feign.hystrix.enabled=true
```

```java
@Service
@FeignClient(value = "MICROSERVICE-PAYMENT", fallback = PaymentFeign.Fallback.class)
public interface PaymentFeign {
    @GetMapping("/sleep/{milliseconds}")
    R<Object> sleep(@PathVariable("milliseconds") Long milliseconds);

    @Service
    class Fallback implements PaymentFeign {
        @Override
        public R<Object> sleep(Long milliseconds) {
            return R.failure(504, "[MICROSERVICE-PAYMENT]服务暂时不可用");
        }
    }
}
```

> 使用 openfeign 并不需要`spring-cloud-starter-netflix-hystrix` 依赖和 `@EnableHystrix`注解。

```java
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
```

```java
@GetMapping("/call_payment/sleep/{milliseconds}")
public R<Object> callPayment(@PathVariable Long milliseconds) {
    R<Object> payment = paymentFeign.sleep(milliseconds);

    Map<String, Object> map = new HashMap<>();
    map.put("applicationName", applicationName);
    map.put("port", port);
    map.put("payment", payment);

    return R.success(map);
}
```

### hystrix 监控端点

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

```properties
management.endpoints.web.exposure.include=hystrix.stream
```

访问：[http://localhost:9031/actuator/hystrix.stream](http://localhost:9031/actuator/hystrix.stream)

### hystrix-dashboard 可视化

> 利用 hystrix-dashboard 可以更直观的看出失败比率、断路器闭合情况等信息。

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix-dashboard</artifactId>
</dependency>
```

```yml
hystrix:
  dashboard:
    proxy-stream-allow-list:
      - localhost
```

```java
@EnableHystrixDashboard
@SpringBootApplication
public class HystrixDashboardApplication {
    public static void main(String[] args) {
        SpringApplication.run(HystrixDashboardApplication.class, args);
    }
}
```

访问：[http://localhost:8090/hystrix](http://localhost:8090/hystrix)

输入监控端点：http://localhost:9031/actuator/hystrix.stream

<br>

## 四、网关

### netflix-zuul

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
</dependency>
```

```yaml
zuul:
  prefix: /api
  routes:
    microservice-ware:
      path: /ware/**
      serviceId: MICROSERVICE-WARE
    microservice-payment:
      path: /payment/**
      serviceId: MICROSERVICE-PAYMENT
    microservie-order:
      path: /order/**
      serviceId: MICROSERVICE-ORDER
```

```java
@EnableZuulProxy
@SpringBootApplication
public class ZuulApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
    }
}
```

## 五、配置中心

### spring-cloud-config 服务端

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-config-server</artifactId>
</dependency>
```

> 客户端访问的服务端地址默认为 8888。

|                         |                                                                                                    |
| ----------------------- | -------------------------------------------------------------------------------------------------- |
| 仓库地址                | [https://gitee.com/icefery/spring-cloud-demo.git](https://gitee.com/icefery/spring-cloud-demo.git) |
| Git 分支                | netflix-dynamic                                                                                    |
| 配置文件目录            | /config/                                                                                           |
| {application}-{profile} | zuul-dev.yml                                                                                       |

```yml
server:
  port: 8888

spring:
  application:
    name: CONFIG-SERVER
  cloud:
    config:
      # Git 分支
      label: netflix-dynamic
      server:
        git:
          # 仓库地址
          uri: https://gitee.com/icefery/spring-cloud-demo
          # 搜索路径
          search-paths:
            - /config/
```

```java
@EnableConfigServer
@EnableDiscoveryClient
@SpringBootApplication
public class ConfigServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}
```

访问：[http://localhost:8888/netflix-dynamic/zuul-dev.yml](http://localhost:8888/netflix-dynamic/zuul-dev.yml)

### spring-cloud-config 客户端

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-config-client</artifactId>
</dependency>
```

> `bootstrap.yml` 会在 `application.yml` 之前加载，因此将配置中心客户端的配置放在`bootstrap.yml` 中以提前拉取好必要的配置文件。

```yml
spring:
  application:
    name: ZUUL
  cloud:
    config:
      # 配置中心地址
      uri:
        - http://localhost:8888
      # Git 分支
      label: netflix-dynamic
      # {application}
      name: zuul
      # {profile}
      profile: dev
```

## 六、消息总线

### spring-cloud-bus 广播配置刷新

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-bus-amqp</artifactId>
</dependency>
```

```yml
spring:
  cloud:
    bus:
      trace:
        enabled: true
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
```

> 将配置中心服务端也作为客户端，并暴露刷新端点。当拉取配置后会通过消息总线广播道其它客户端。

```yml
management:
  endpoints:
    web:
      exposure:
        include: bus-refresh
```

```java
@Configuration
public class ZuulConfig {
    @RefreshScope
    @ConfigurationProperties("zuul")
    public ZuulProperties zuulProperties() {
        return new ZuulProperties();
    }
}
```

```shell
# 拉取配置
curl -X POST http://localhost:8888/actuator/bus-refresh
```
