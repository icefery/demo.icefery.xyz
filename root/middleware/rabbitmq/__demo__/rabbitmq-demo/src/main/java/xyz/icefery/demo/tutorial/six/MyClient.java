package xyz.icefery.demo.tutorial.six;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import xyz.icefery.demo.util.MyRabbitMQ;

/**
 * 远程调用模式-客户端
 */
public class MyClient {

    static final String REQUEST_EXCHANGE = MyServer.REQUEST_EXCHANGE; // 请求交换机
    static final String REQUEST_ROUTING_KEY = MyServer.REQUEST_QUEUE; // 请求路由键
    static final String RESPONSE_EXCHANGE = ""; // 响应交换机

    public static void main(String[] args) {
        MyRabbitMQ.start(MyClient.class.getSimpleName(), true, channel -> {
            for (int i = 0; i < 10; i++) {
                int f = f(channel, i);
                System.out.printf("f(%d) = %d\n", i, f);
            }
        });
    }

    private static int f(Channel channel, int n) throws Exception {
        // 1. 请求头
        String requestId = UUID.randomUUID().toString(); // 请求 ID(服务端响应时携带请求 ID，客户端收到响应后根据判断请求 ID 是否一致，不一致可进行丢弃)
        String responseQueue = channel.queueDeclare().getQueue(); // 响应队列(每个请求单独创建临时队列，服务端将响应消息发送至该队列中)
        AMQP.BasicProperties requestHeaders = new AMQP.BasicProperties.Builder().correlationId(requestId).replyTo(responseQueue).build();

        // 2. 请求体
        String requestBody = Integer.toString(n);

        channel.basicPublish(REQUEST_EXCHANGE, REQUEST_ROUTING_KEY, requestHeaders, requestBody.getBytes());
        System.out.printf("Requested requestId='%s' requestBody='%s' responseQueue='%s'\n", requestId, requestBody, responseQueue);

        BlockingQueue<String> response = new ArrayBlockingQueue<>(1); // 响应结果阻塞队列(一次只发起一条请求并等待请求响应)

        DeliverCallback responseCallback = (consumerTag, delivery) -> {
            // 3. 响应头
            AMQP.BasicProperties responseHeaders = delivery.getProperties();
            if (Objects.equals(responseHeaders.getCorrelationId(), requestId)) {
                // 4. 响应体
                String responseBody = new String(delivery.getBody());
                System.out.printf(
                    "Received requestId='%s' requestBody='%s' responseQueue='%s' responseBody='%s' \n",
                    requestId,
                    requestBody,
                    responseQueue,
                    responseBody
                );
                response.offer(responseBody);
            }
        };

        String cTag = channel.basicConsume(responseQueue, true, responseCallback, consumerTag -> {});
        String responseBody = response.take(); // 等待响应结果
        channel.basicCancel(cTag); // 取消对响应队列的订阅

        return Integer.parseInt(responseBody);
    }
}
