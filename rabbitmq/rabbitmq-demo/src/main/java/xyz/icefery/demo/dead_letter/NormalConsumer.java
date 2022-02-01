package xyz.icefery.demo.dead_letter;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.DeliverCallback;
import xyz.icefery.demo.util.MyRabbitMQ;
import java.util.Map;

/**
 * 死信队列-正常消费者
 */
public class NormalConsumer {
    static final String EXCHANGE = "x.normal.direct";
    static final String QUEUE = "q.normal";
    static final String ROUTING_KEY = "";

    static final String DEAD_LETTER_EXCHANGE = DeadLetterConsumer.EXCHANGE;
    static final String DEAD_LETTER_ROUTING_KEY = DeadLetterConsumer.ROUTING_KEY;

    public static void main(String[] args) {
        MyRabbitMQ.start(NormalConsumer.class.getSimpleName(), false, channel -> {
            channel.exchangeDelete(EXCHANGE);
            channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.DIRECT);

            channel.queueDelete(QUEUE);
            // 正常队列绑定死信交换机
            Map<String, Object> arguments = Map.of(
                "x-dead-letter-exchange", DEAD_LETTER_EXCHANGE,       // 设置死信交换机
                "x-dead-letter-routing-key", DEAD_LETTER_ROUTING_KEY, // 设置死信路由键
                "x-message-ttl", 1000 * 60,                           // 设置队列消息存活时间
                "x-max-length", 10                                    // 设置队列消息最大容量(先入队列的会先进入死信队列)
            );
            channel.queueDeclare(QUEUE, false, false, false, arguments);

            channel.queueBind(QUEUE, EXCHANGE, ROUTING_KEY);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                long deliveryTag = delivery.getEnvelope().getDeliveryTag();
                String body = new String(delivery.getBody());
                String status;
                try {
                    Integer.parseInt(body.split(" ")[0]);
                    channel.basicAck(deliveryTag, false);
                    status = "ack";
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    // 消息被拒
                    channel.basicNack(deliveryTag, false, false);
                    status = "nack";
                }
                System.out.printf("Received message='%s' status='%s'\n", body, status);
            };
            channel.basicConsume(QUEUE, false, deliverCallback, consumerTag -> { });
        });
    }
}
