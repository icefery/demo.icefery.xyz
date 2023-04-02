package xyz.icefery.ice.rpc.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import xyz.icefery.ice.rpc.common.RPCRequest;
import xyz.icefery.ice.rpc.common.RPCResponse;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Slf4j
public class RPCClientHandler extends ChannelInboundHandlerAdapter {
    private final ObjectMapper objectMapper;
    private final BlockingQueue<RPCResponse> blockingQueue;

    private ChannelHandlerContext context;

    public RPCClientHandler() {
        this.objectMapper = new ObjectMapper();
        this.blockingQueue = new ArrayBlockingQueue<>(1);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.context = ctx;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RPCResponse response = objectMapper.readValue(((String) msg), RPCResponse.class);
        blockingQueue.put(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        String message = "failure to call request";
        log.error(message, cause);
        ctx.close();
        throw new RuntimeException(message);
    }

    public RPCResponse call(RPCRequest request) {
        try {
            String json = new ObjectMapper().writeValueAsString(request);
            context.writeAndFlush(json);
            return blockingQueue.take();
        } catch (Exception e) {
            String message = String.format("failure to call request {request=%s}", request.toString());
            log.error(message, e);
            throw new RuntimeException(message);
        }
    }
}
