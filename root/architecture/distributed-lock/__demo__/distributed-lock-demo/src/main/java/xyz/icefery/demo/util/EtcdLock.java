package xyz.icefery.demo.util;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.lease.LeaseKeepAliveResponse;
import io.etcd.jetcd.options.GetOption;
import io.etcd.jetcd.options.PutOption;
import io.grpc.stub.StreamObserver;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.util.function.Tuple3;
import reactor.util.function.Tuples;

@Slf4j
public class EtcdLock extends AbstractLock {

    // [leaseId, key, depth]
    private static final ThreadLocal<Tuple3<Long, String, Integer>> THREAD_LOCAL = new ThreadLocal<>();

    private final Client client;
    private final String name;
    private final String baseKey;

    public EtcdLock(Client client, String name) {
        this.client = client;
        this.name = name;
        this.baseKey = "/" + name;
    }

    @SneakyThrows
    @Override
    public void lock() {
        Tuple3<Long, String, Integer> tuple = THREAD_LOCAL.get();
        if (tuple != null && tuple.getT3() > 0) {
            tuple = Tuples.of(tuple.getT1(), tuple.getT2(), tuple.getT3() + 1);
        } else {
            long leaseId = client.getLeaseClient().grant(30L).get().getID();
            client
                .getLeaseClient()
                .keepAlive(
                    leaseId,
                    new StreamObserver<LeaseKeepAliveResponse>() {
                        @Override
                        public void onNext(LeaseKeepAliveResponse value) {}

                        @Override
                        public void onError(Throwable t) {}

                        @Override
                        public void onCompleted() {}
                    }
                );
            String key = baseKey + "/" + leaseId;
            PutOption putOption = PutOption.newBuilder().withLeaseId(leaseId).build();
            client.getKVClient().put(ByteSequence.from(key.getBytes()), ByteSequence.EMPTY, putOption).get();
            String prefix = "/" + name;
            GetOption getOption = GetOption.newBuilder().isPrefix(true).withSortField(GetOption.SortTarget.CREATE).build();
            while (
                !Objects.equals(
                    client
                        .getKVClient()
                        .get(ByteSequence.from(prefix.getBytes()), getOption)
                        .get()
                        .getKvs()
                        .stream()
                        .map(it -> it.getKey().toString())
                        .toList()
                        .get(0),
                    key
                )
            ) {
                TimeUnit.MILLISECONDS.sleep(100);
            }
            tuple = Tuples.of(leaseId, key, 1);
        }
        THREAD_LOCAL.set(tuple);
        log.info("[locked] thread={} depth={}", tuple.getT2(), tuple.getT3());
    }

    @SneakyThrows
    @Override
    public void unlock() {
        Tuple3<Long, String, Integer> tuple = THREAD_LOCAL.get();
        if (tuple != null && tuple.getT3() > 0) {
            tuple = Tuples.of(tuple.getT1(), tuple.getT2(), tuple.getT3() - 1);
            THREAD_LOCAL.set(tuple);
            if (tuple.getT3() == 0) {
                client.getKVClient().delete(ByteSequence.from(tuple.getT2().getBytes())).get();
                client.getLeaseClient().revoke(tuple.getT1()).get();
                THREAD_LOCAL.remove();
            }
            log.info("[unlocked] thread={} depth={}", tuple.getT1(), tuple.getT3());
        }
    }
}
