package xyz.icefery.ice.rpc.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.lang.reflect.Method;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import xyz.icefery.ice.rpc.common.RPCRequest;
import xyz.icefery.ice.rpc.common.RPCResponse;

@Slf4j
public class RPCServerHandler extends ChannelInboundHandlerAdapter {

    private final Map<String, ?> apiMap;
    private final ObjectMapper objectMapper;

    public RPCServerHandler(Map<String, ?> apiMap) {
        this.apiMap = apiMap;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RPCRequest request = objectMapper.readValue(((String) msg), RPCRequest.class);
        log.info("request={}", request);

        Object instance = apiMap.get(request.getClassName());
        Class<?> clazz = instance.getClass();
        Method method = clazz.getMethod(request.getMethodName(), request.getParameterTypes());
        Object result = method.invoke(instance, request.getArguments());

        RPCResponse response = new RPCResponse(request.getRequestId(), result);
        msg = objectMapper.writeValueAsString(response);
        ctx.writeAndFlush(msg);
        log.info("response={}", response);
    }
}
