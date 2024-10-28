package xyz.icefery.demo.dead_letter;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.DeliverCallback;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import xyz.icefery.demo.util.MyRabbitMQ;

/**
 * 死信队列-死信消费者
 */
public class DeadLetterConsumer {

    static final String EXCHANGE = "x.dead_letter.direct";
    static final String QUEUE = "q.dead_letter";
    static final String ROUTING_KEY = "";

    public static void main(String[] args) {
        MyRabbitMQ.start(DeadLetterConsumer.class.getSimpleName(), false, channel -> {
            channel.exchangeDelete(EXCHANGE);
            channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.DIRECT);

            channel.queueDelete(QUEUE);
            channel.queueDeclare(QUEUE, false, false, false, null);

            channel.queueBind(QUEUE, EXCHANGE, ROUTING_KEY);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String body = new String(delivery.getBody());
                String at = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                System.out.printf("Received message='%s' at='%s'\n", body, at);
            };
            channel.basicConsume(QUEUE, true, deliverCallback, consumerTag -> {});
        });
    }
}
