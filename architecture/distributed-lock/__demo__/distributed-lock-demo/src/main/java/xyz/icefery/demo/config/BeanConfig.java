package xyz.icefery.demo.config;

import io.etcd.jetcd.Client;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.192.192.101:6379").setDatabase(0).setPassword("redis");
        return Redisson.create(config);
    }

    @Bean
    public CuratorFramework curatorFramework() {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient("192.192.192.101:2181", new RetryNTimes(3, 3000));
        curatorFramework.start();
        return curatorFramework;
    }

    @Bean
    public Client client() {
        return Client.builder().endpoints("http://192.192.192.101:2379").build();
    }
}
