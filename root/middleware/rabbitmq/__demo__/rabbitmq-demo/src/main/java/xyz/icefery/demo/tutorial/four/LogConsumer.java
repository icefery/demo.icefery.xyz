package xyz.icefery.demo.tutorial.four;

import com.rabbitmq.client.DeliverCallback;
import xyz.icefery.demo.util.MyRabbitMQ;

/**
 * 路由模式-收集所有日志消费者
 */
public class LogConsumer {

    static final String QUEUE = "q.tutorial_four.log";

    public static void main(String[] args) {
        MyRabbitMQ.start(LogConsumer.QUEUE, false, channel -> {
            channel.queueDeclare(QUEUE, false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                System.out.printf("Received message='%s'\n", new String(delivery.getBody()));
            };
            channel.basicConsume(QUEUE, true, deliverCallback, consumerTag -> {});
        });
    }
}
