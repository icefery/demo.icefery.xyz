package xyz.icefery.ice.rpc.common;

import java.util.List;

public interface LoadBalancer<T> {
    T select(List<T> list);
}
