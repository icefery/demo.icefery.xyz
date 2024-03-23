package xyz.icefery.demo.delayed;

import com.rabbitmq.client.DeliverCallback;
import xyz.icefery.demo.util.MyRabbitMQ;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * 延迟队列-消费者
 */
public class MyConsumer {
    static final String EXCHANGE = "x.delayed.x-delayed-message";
    static final String QUEUE = "q.delayed";
    static final String ROUTING_KEY = "";

    public static void main(String[] args) {
        MyRabbitMQ.start(MyConsumer.class.getSimpleName(), false, channel -> {
            channel.exchangeDelete(EXCHANGE);
            Map<String, Object> arguments = Map.of("x-delayed-type", "direct");
            channel.exchangeDeclare(EXCHANGE, "x-delayed-message", false, false, arguments);

            channel.queueDelete(QUEUE);
            channel.queueDeclare(QUEUE, false, false, false, null);

            channel.queueBind(QUEUE, EXCHANGE, ROUTING_KEY);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String body = new String(delivery.getBody());
                String at = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                System.out.printf("Received message='%s' at='%s'\n", body, at);
            };
            channel.basicConsume(QUEUE, true, deliverCallback, consumerTag -> { });
        });
    }
}
