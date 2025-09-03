package xyz.icefery.demo.tutorial.three;

import com.rabbitmq.client.DeliverCallback;
import xyz.icefery.demo.util.MyRabbitMQ;

/**
 * 发布订阅模式-消费者-2
 */
public class MyConsumer2 {

    static final String QUEUE = "q.tutorial_three.queue2";

    public static void main(String[] args) {
        MyRabbitMQ.start(MyConsumer2.class.getSimpleName(), false, channel -> {
            channel.queueDeclare(QUEUE, false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                System.out.printf("Received message='%s'\n", new String(delivery.getBody()));
            };
            channel.basicConsume(QUEUE, true, deliverCallback, consumerTag -> {});
        });
    }
}
