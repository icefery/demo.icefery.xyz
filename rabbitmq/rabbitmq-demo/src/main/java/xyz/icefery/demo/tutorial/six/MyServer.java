package xyz.icefery.demo.tutorial.six;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.DeliverCallback;
import xyz.icefery.demo.util.MyRabbitMQ;

/**
 * 远程调用模式-服务端
 */
public class MyServer {
    static final String REQUEST_EXCHANGE = "";                          // 请求交换机
    static final String REQUEST_QUEUE = "q.tutorial_six.request";       // 请求队列
    static final String RESPONSE_EXCHANGE = MyClient.RESPONSE_EXCHANGE; // 响应交换机

    public static void main(String[] args) {
        MyRabbitMQ.start(MyServer.class.getSimpleName(), false, channel -> {
            channel.queueDeclare(REQUEST_QUEUE, false, false, false, null);

            channel.basicQos(1); // 限制未响应请求缓冲区大小(一次只处理一条请求)

            DeliverCallback handler = (consumerTag, delivery) -> {
                // 1. 请求头
                AMQP.BasicProperties requestHeaders = delivery.getProperties();
                String requestId = requestHeaders.getCorrelationId();
                String responseRoutingKey = requestHeaders.getReplyTo();

                // 2. 请求体
                String requestBody = new String(delivery.getBody());
                System.out.printf("Received requestId='%s' requestBody='%s' responseRoutingKey='%s'\n", requestId, requestBody, responseRoutingKey);

                // 3. 响应头
                AMQP.BasicProperties responseHeaders = new AMQP.BasicProperties
                    .Builder()
                    .correlationId(requestId)
                    .build();

                // 4. 响应体
                String responseBody = Integer.toString(f(Integer.parseInt(requestBody)));

                channel.basicPublish(RESPONSE_EXCHANGE, responseRoutingKey, responseHeaders, responseBody.getBytes());
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                System.out.printf("Responded requestId='%s' requestBody='%s' responseRoutingKey='%s' responseBody='%s'\n", requestId, requestBody, responseRoutingKey, responseBody);
            };
            channel.basicConsume(REQUEST_QUEUE, false, handler, consumerTag -> { });
        });
    }

    private static int f(int n) {
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        } else {
            return f(n - 1) + f(n - 2);
        }
    }
}
