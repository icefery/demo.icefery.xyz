package org.example;

import generated.FunctionGrpc;
import generated.FunctionOuterClass;
import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.example.util.OSUtil;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    private static final int PORT = 8081;

    public static void main(String[] args) throws Exception {
        FunctionGrpc.FunctionImplBase functionService = new FunctionGrpc.FunctionImplBase() {
            @Override
            public void fetch(FunctionOuterClass.FetchRequest request, StreamObserver<FunctionOuterClass.FetchResponse> responseObserver) {
                FunctionOuterClass.Instance fetch = FunctionOuterClass.Instance
                    .newBuilder()
                    .setName("fetch")
                    .setAddr(String.format("%s:%d", OSUtil.hostname(), PORT))
                    .setPlatform(OSUtil.platform())
                    .setLanguage("java")
                    .setProtocol("grpc")
                    .build();
                FunctionOuterClass.Instance invoke = FunctionOuterClass.Instance
                    .newBuilder()
                    .setName("invoke")
                    .setAddr(String.format("%s:%d", OSUtil.hostname(), PORT))
                    .setPlatform(OSUtil.platform())
                    .setLanguage("java")
                    .setProtocol("grpc")
                    .build();
                FunctionOuterClass.FetchResponse response = FunctionOuterClass.FetchResponse
                    .newBuilder()
                    .addInstances(fetch)
                    .addInstances(invoke)
                    .build();

                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }

            @Override
            public void invoke(FunctionOuterClass.InvokeRequest request, StreamObserver<FunctionOuterClass.InvokeResponse> responseObserver) {
                Map<String, FunctionOuterClass.Invocation> results = new ConcurrentHashMap<>();
                request.getInvocationsList().parallelStream().forEach(invocation -> {
                    String host = invocation.getHost();
                    int port = invocation.getPort();

                    ManagedChannel channel = ManagedChannelBuilder
                        .forAddress(host, port)
                        .usePlaintext()
                        .build();
                    FunctionOuterClass.FetchRequest fetchRequest = FunctionOuterClass.FetchRequest
                        .newBuilder()
                        .build();
                    FunctionOuterClass.FetchResponse fetchResponse = FunctionGrpc
                        .newBlockingStub(channel)
                        .fetch(fetchRequest);

                    String key = String.format("%s:%d", host, port);
                    FunctionOuterClass.Invocation value = FunctionOuterClass.Invocation
                        .newBuilder()
                        .addAllInstances(fetchResponse.getInstancesList())
                        .build();
                    results.put(key, value);
                });

                FunctionOuterClass.InvokeResponse response = FunctionOuterClass.InvokeResponse
                    .newBuilder()
                    .putAllResults(results)
                    .build();

                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }
        };

        Grpc
            .newServerBuilderForPort(PORT, InsecureServerCredentials.create())
            .addService(functionService)
            .build()
            .start()
            .awaitTermination();
    }
}
