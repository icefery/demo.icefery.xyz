package xyz.icefery.ice.rpc.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import xyz.icefery.ice.rpc.common.LoadBalancer;
import xyz.icefery.ice.rpc.common.RPCRequest;
import xyz.icefery.ice.rpc.common.RPCResponse;
import xyz.icefery.ice.rpc.service.discovery.ServiceDiscovery;
import java.lang.reflect.Proxy;
import java.util.List;

@Slf4j
public class RPCClient {
    private final ServiceDiscovery serviceDiscovery;
    private final LoadBalancer<String> loadBalancer;

    public RPCClient(ServiceDiscovery serviceDiscovery, LoadBalancer<String> loadBalancer) {
        this.serviceDiscovery = serviceDiscovery;
        this.loadBalancer = loadBalancer;
    }

    public RPCResponse call(RPCRequest request) {
        RPCClientHandler RPCClientHandler = new RPCClientHandler();
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            // 负载均衡
            List<String> addressList = serviceDiscovery.instances().get(request.getServiceName());
            String address = loadBalancer.select(addressList);
            // 服务调用
            bootstrap
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch
                            .pipeline()
                            .addLast(new StringDecoder(CharsetUtil.UTF_8))
                            .addLast(new StringEncoder(CharsetUtil.UTF_8))
                            .addLast(RPCClientHandler)
                        ;
                    }
                })
                .connect(address.split(":")[0], Integer.parseInt(address.split(":")[1]))
                .sync();
            return RPCClientHandler.call(request);
        } catch (Exception e) {
            String message = String.format("failure to call request {request=%s}", request.toString());
            log.error(message, e);
            throw new RuntimeException(e);
        } finally {
            group.shutdownGracefully();
        }
    }

    public <T> T reference(Class<T> interfaceClass) {
        Object proxyInstance = Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass}, (proxy, method, args) -> {
            RPCReference annotation = method.getAnnotation(RPCReference.class);
            RPCRequest request = new RPCRequest()
                .setServiceName(annotation.serviceName())
                .setClassName(annotation.className())
                .setMethodName(annotation.methodName())
                .setParameterTypes(method.getParameterTypes())
                .setArguments(args);
            RPCResponse response = RPCClient.this.call(request);
            return response.getResult();
        });
        return ((T) proxyInstance);
    }
}
