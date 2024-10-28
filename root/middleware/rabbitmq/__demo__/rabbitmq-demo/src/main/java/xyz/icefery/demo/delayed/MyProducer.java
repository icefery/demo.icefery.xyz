package xyz.icefery.demo.delayed;

import com.rabbitmq.client.AMQP;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import xyz.icefery.demo.util.MyRabbitMQ;

/**
 * 延迟队列-生产者
 */
public class MyProducer {

    static final String EXCHANGE = MyConsumer.EXCHANGE;
    static final String ROUTING_KEY = MyConsumer.ROUTING_KEY;

    public static void main(String[] args) {
        MyRabbitMQ.start(MyProducer.class.getSimpleName(), true, channel -> {
            for (int i = 1; i <= 10; i++) {
                AMQP.BasicProperties headers = new AMQP.BasicProperties.Builder().headers(Map.of("x-delay", Long.toString(i * 1000L))).build();
                String body = i + " " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                channel.basicPublish(EXCHANGE, ROUTING_KEY, headers, body.getBytes());
                System.out.printf("Sent message='%s'\n", body);
                TimeUnit.MILLISECONDS.sleep(1000);
            }
        });
    }
}
