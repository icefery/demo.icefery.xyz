package generated;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(value = "by gRPC proto compiler (version 1.54.1)", comments = "Source: function.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class FunctionGrpc {

    private FunctionGrpc() {}

    public static final String SERVICE_NAME = "Function";

    // Static method descriptors that strictly reflect the proto.
    private static volatile io.grpc.MethodDescriptor<generated.FunctionOuterClass.FetchRequest, generated.FunctionOuterClass.FetchResponse> getFetchMethod;

    @io.grpc.stub.annotations.RpcMethod(
        fullMethodName = SERVICE_NAME + '/' + "fetch",
        requestType = generated.FunctionOuterClass.FetchRequest.class,
        responseType = generated.FunctionOuterClass.FetchResponse.class,
        methodType = io.grpc.MethodDescriptor.MethodType.UNARY
    )
    public static io.grpc.MethodDescriptor<generated.FunctionOuterClass.FetchRequest, generated.FunctionOuterClass.FetchResponse> getFetchMethod() {
        io.grpc.MethodDescriptor<generated.FunctionOuterClass.FetchRequest, generated.FunctionOuterClass.FetchResponse> getFetchMethod;
        if ((getFetchMethod = FunctionGrpc.getFetchMethod) == null) {
            synchronized (FunctionGrpc.class) {
                if ((getFetchMethod = FunctionGrpc.getFetchMethod) == null) {
                    FunctionGrpc.getFetchMethod =
                        getFetchMethod = io.grpc.MethodDescriptor.<
                            generated.FunctionOuterClass.FetchRequest,
                            generated.FunctionOuterClass.FetchResponse
                        >newBuilder()
                            .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                            .setFullMethodName(generateFullMethodName(SERVICE_NAME, "fetch"))
                            .setSampledToLocalTracing(true)
                            .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(generated.FunctionOuterClass.FetchRequest.getDefaultInstance()))
                            .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(generated.FunctionOuterClass.FetchResponse.getDefaultInstance()))
                            .setSchemaDescriptor(new FunctionMethodDescriptorSupplier("fetch"))
                            .build();
                }
            }
        }
        return getFetchMethod;
    }

    private static volatile io.grpc.MethodDescriptor<generated.FunctionOuterClass.InvokeRequest, generated.FunctionOuterClass.InvokeResponse> getInvokeMethod;

    @io.grpc.stub.annotations.RpcMethod(
        fullMethodName = SERVICE_NAME + '/' + "invoke",
        requestType = generated.FunctionOuterClass.InvokeRequest.class,
        responseType = generated.FunctionOuterClass.InvokeResponse.class,
        methodType = io.grpc.MethodDescriptor.MethodType.UNARY
    )
    public static io.grpc.MethodDescriptor<generated.FunctionOuterClass.InvokeRequest, generated.FunctionOuterClass.InvokeResponse> getInvokeMethod() {
        io.grpc.MethodDescriptor<generated.FunctionOuterClass.InvokeRequest, generated.FunctionOuterClass.InvokeResponse> getInvokeMethod;
        if ((getInvokeMethod = FunctionGrpc.getInvokeMethod) == null) {
            synchronized (FunctionGrpc.class) {
                if ((getInvokeMethod = FunctionGrpc.getInvokeMethod) == null) {
                    FunctionGrpc.getInvokeMethod =
                        getInvokeMethod = io.grpc.MethodDescriptor.<
                            generated.FunctionOuterClass.InvokeRequest,
                            generated.FunctionOuterClass.InvokeResponse
                        >newBuilder()
                            .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                            .setFullMethodName(generateFullMethodName(SERVICE_NAME, "invoke"))
                            .setSampledToLocalTracing(true)
                            .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(generated.FunctionOuterClass.InvokeRequest.getDefaultInstance()))
                            .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(generated.FunctionOuterClass.InvokeResponse.getDefaultInstance()))
                            .setSchemaDescriptor(new FunctionMethodDescriptorSupplier("invoke"))
                            .build();
                }
            }
        }
        return getInvokeMethod;
    }

    /**
     * Creates a new async stub that supports all call types for the service
     */
    public static FunctionStub newStub(io.grpc.Channel channel) {
        io.grpc.stub.AbstractStub.StubFactory<FunctionStub> factory = new io.grpc.stub.AbstractStub.StubFactory<FunctionStub>() {
            @java.lang.Override
            public FunctionStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
                return new FunctionStub(channel, callOptions);
            }
        };
        return FunctionStub.newStub(factory, channel);
    }

    /**
     * Creates a new blocking-style stub that supports unary and streaming output calls on the service
     */
    public static FunctionBlockingStub newBlockingStub(io.grpc.Channel channel) {
        io.grpc.stub.AbstractStub.StubFactory<FunctionBlockingStub> factory = new io.grpc.stub.AbstractStub.StubFactory<FunctionBlockingStub>() {
            @java.lang.Override
            public FunctionBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
                return new FunctionBlockingStub(channel, callOptions);
            }
        };
        return FunctionBlockingStub.newStub(factory, channel);
    }

    /**
     * Creates a new ListenableFuture-style stub that supports unary calls on the service
     */
    public static FunctionFutureStub newFutureStub(io.grpc.Channel channel) {
        io.grpc.stub.AbstractStub.StubFactory<FunctionFutureStub> factory = new io.grpc.stub.AbstractStub.StubFactory<FunctionFutureStub>() {
            @java.lang.Override
            public FunctionFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
                return new FunctionFutureStub(channel, callOptions);
            }
        };
        return FunctionFutureStub.newStub(factory, channel);
    }

    /**
     */
    public interface AsyncService {
        /**
         */
        default void fetch(
            generated.FunctionOuterClass.FetchRequest request,
            io.grpc.stub.StreamObserver<generated.FunctionOuterClass.FetchResponse> responseObserver
        ) {
            io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getFetchMethod(), responseObserver);
        }

        /**
         */
        default void invoke(
            generated.FunctionOuterClass.InvokeRequest request,
            io.grpc.stub.StreamObserver<generated.FunctionOuterClass.InvokeResponse> responseObserver
        ) {
            io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getInvokeMethod(), responseObserver);
        }
    }

    /**
     * Base class for the server implementation of the service Function.
     */
    public abstract static class FunctionImplBase implements io.grpc.BindableService, AsyncService {

        @java.lang.Override
        public final io.grpc.ServerServiceDefinition bindService() {
            return FunctionGrpc.bindService(this);
        }
    }

    /**
     * A stub to allow clients to do asynchronous rpc calls to service Function.
     */
    public static final class FunctionStub extends io.grpc.stub.AbstractAsyncStub<FunctionStub> {

        private FunctionStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @java.lang.Override
        protected FunctionStub build(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new FunctionStub(channel, callOptions);
        }

        /**
         */
        public void fetch(
            generated.FunctionOuterClass.FetchRequest request,
            io.grpc.stub.StreamObserver<generated.FunctionOuterClass.FetchResponse> responseObserver
        ) {
            io.grpc.stub.ClientCalls.asyncUnaryCall(getChannel().newCall(getFetchMethod(), getCallOptions()), request, responseObserver);
        }

        /**
         */
        public void invoke(
            generated.FunctionOuterClass.InvokeRequest request,
            io.grpc.stub.StreamObserver<generated.FunctionOuterClass.InvokeResponse> responseObserver
        ) {
            io.grpc.stub.ClientCalls.asyncUnaryCall(getChannel().newCall(getInvokeMethod(), getCallOptions()), request, responseObserver);
        }
    }

    /**
     * A stub to allow clients to do synchronous rpc calls to service Function.
     */
    public static final class FunctionBlockingStub extends io.grpc.stub.AbstractBlockingStub<FunctionBlockingStub> {

        private FunctionBlockingStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @java.lang.Override
        protected FunctionBlockingStub build(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new FunctionBlockingStub(channel, callOptions);
        }

        /**
         */
        public generated.FunctionOuterClass.FetchResponse fetch(generated.FunctionOuterClass.FetchRequest request) {
            return io.grpc.stub.ClientCalls.blockingUnaryCall(getChannel(), getFetchMethod(), getCallOptions(), request);
        }

        /**
         */
        public generated.FunctionOuterClass.InvokeResponse invoke(generated.FunctionOuterClass.InvokeRequest request) {
            return io.grpc.stub.ClientCalls.blockingUnaryCall(getChannel(), getInvokeMethod(), getCallOptions(), request);
        }
    }

    /**
     * A stub to allow clients to do ListenableFuture-style rpc calls to service Function.
     */
    public static final class FunctionFutureStub extends io.grpc.stub.AbstractFutureStub<FunctionFutureStub> {

        private FunctionFutureStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @java.lang.Override
        protected FunctionFutureStub build(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new FunctionFutureStub(channel, callOptions);
        }

        /**
         */
        public com.google.common.util.concurrent.ListenableFuture<generated.FunctionOuterClass.FetchResponse> fetch(
            generated.FunctionOuterClass.FetchRequest request
        ) {
            return io.grpc.stub.ClientCalls.futureUnaryCall(getChannel().newCall(getFetchMethod(), getCallOptions()), request);
        }

        /**
         */
        public com.google.common.util.concurrent.ListenableFuture<generated.FunctionOuterClass.InvokeResponse> invoke(
            generated.FunctionOuterClass.InvokeRequest request
        ) {
            return io.grpc.stub.ClientCalls.futureUnaryCall(getChannel().newCall(getInvokeMethod(), getCallOptions()), request);
        }
    }

    private static final int METHODID_FETCH = 0;
    private static final int METHODID_INVOKE = 1;

    private static final class MethodHandlers<Req, Resp>
        implements
            io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {

        private final AsyncService serviceImpl;
        private final int methodId;

        MethodHandlers(AsyncService serviceImpl, int methodId) {
            this.serviceImpl = serviceImpl;
            this.methodId = methodId;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("unchecked")
        public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch (methodId) {
                case METHODID_FETCH:
                    serviceImpl.fetch(
                        (generated.FunctionOuterClass.FetchRequest) request,
                        (io.grpc.stub.StreamObserver<generated.FunctionOuterClass.FetchResponse>) responseObserver
                    );
                    break;
                case METHODID_INVOKE:
                    serviceImpl.invoke(
                        (generated.FunctionOuterClass.InvokeRequest) request,
                        (io.grpc.stub.StreamObserver<generated.FunctionOuterClass.InvokeResponse>) responseObserver
                    );
                    break;
                default:
                    throw new AssertionError();
            }
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("unchecked")
        public io.grpc.stub.StreamObserver<Req> invoke(io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch (methodId) {
                default:
                    throw new AssertionError();
            }
        }
    }

    public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
        return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
            .addMethod(
                getFetchMethod(),
                io.grpc.stub.ServerCalls.asyncUnaryCall(
                    new MethodHandlers<generated.FunctionOuterClass.FetchRequest, generated.FunctionOuterClass.FetchResponse>(service, METHODID_FETCH)
                )
            )
            .addMethod(
                getInvokeMethod(),
                io.grpc.stub.ServerCalls.asyncUnaryCall(
                    new MethodHandlers<generated.FunctionOuterClass.InvokeRequest, generated.FunctionOuterClass.InvokeResponse>(service, METHODID_INVOKE)
                )
            )
            .build();
    }

    private abstract static class FunctionBaseDescriptorSupplier
        implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {

        FunctionBaseDescriptorSupplier() {}

        @java.lang.Override
        public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
            return generated.FunctionOuterClass.getDescriptor();
        }

        @java.lang.Override
        public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
            return getFileDescriptor().findServiceByName("Function");
        }
    }

    private static final class FunctionFileDescriptorSupplier extends FunctionBaseDescriptorSupplier {

        FunctionFileDescriptorSupplier() {}
    }

    private static final class FunctionMethodDescriptorSupplier
        extends FunctionBaseDescriptorSupplier
        implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {

        private final String methodName;

        FunctionMethodDescriptorSupplier(String methodName) {
            this.methodName = methodName;
        }

        @java.lang.Override
        public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
            return getServiceDescriptor().findMethodByName(methodName);
        }
    }

    private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

    public static io.grpc.ServiceDescriptor getServiceDescriptor() {
        io.grpc.ServiceDescriptor result = serviceDescriptor;
        if (result == null) {
            synchronized (FunctionGrpc.class) {
                result = serviceDescriptor;
                if (result == null) {
                    serviceDescriptor =
                        result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
                            .setSchemaDescriptor(new FunctionFileDescriptorSupplier())
                            .addMethod(getFetchMethod())
                            .addMethod(getInvokeMethod())
                            .build();
                }
            }
        }
        return result;
    }
}
