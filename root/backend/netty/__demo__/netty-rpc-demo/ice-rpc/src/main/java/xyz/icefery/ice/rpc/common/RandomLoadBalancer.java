package xyz.icefery.ice.rpc.common;

import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Random;

@Slf4j
public class RandomLoadBalancer<T> implements LoadBalancer<T> {
    @Override
    public T select(List<T> list) {
        if (list.isEmpty()) {
            String message = String.format("failure to load balance {list.size=%s}", list.size());
            log.error(message);
            throw new RuntimeException(message);
        }
        int index = new Random().nextInt(0, list.size());
        return list.get(index);
    }
}
