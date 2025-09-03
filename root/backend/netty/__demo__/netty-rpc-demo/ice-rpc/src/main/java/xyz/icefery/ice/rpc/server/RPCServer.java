package xyz.icefery.ice.rpc.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import xyz.icefery.ice.rpc.service.discovery.ServiceDiscovery;

@Slf4j
public class RPCServer {

    private final String serviceName;
    private final String serviceHost;
    private final Integer servicePort;
    private final String serviceAddress;
    private final ServiceDiscovery serviceDiscovery;
    private final Map<String, ?> apiMap;

    public RPCServer(String serviceName, Integer servicePort, ServiceDiscovery serviceDiscovery, Map<String, ?> apiMap) {
        try {
            this.serviceName = serviceName;
            this.serviceHost = InetAddress.getLocalHost().getHostAddress();
            this.servicePort = servicePort;
            this.serviceAddress = this.serviceHost + ":" + servicePort;
            this.serviceDiscovery = serviceDiscovery;
            this.apiMap = apiMap;
        } catch (UnknownHostException e) {
            String message = "failure to init server";
            log.error(message, e);
            throw new RuntimeException(message);
        }
    }

    public void start() throws Exception {
        // 服务注册
        serviceDiscovery.register(serviceName, serviceAddress);
        // 服务启动
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            bootstrap
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(
                    new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch
                                .pipeline()
                                .addLast(new StringDecoder(CharsetUtil.UTF_8))
                                .addLast(new StringEncoder(CharsetUtil.UTF_8))
                                .addLast(new RPCServerHandler(apiMap));
                        }
                    }
                )
                .bind(serviceHost, servicePort)
                .sync()
                .addListener(future -> {
                    if (future.isSuccess()) {
                        log.info("success to listening on {}", serviceAddress);
                    }
                })
                .channel()
                .closeFuture()
                .sync();
        } finally {
            worker.shutdownGracefully();
            boss.shutdownGracefully();
        }
    }
}
