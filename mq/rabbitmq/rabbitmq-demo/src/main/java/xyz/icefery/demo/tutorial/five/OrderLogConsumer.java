package xyz.icefery.demo.tutorial.five;

import com.rabbitmq.client.DeliverCallback;
import xyz.icefery.demo.util.MyRabbitMQ;

/**
 * 主题模式-收集订单服务日志消费者
 */
public class OrderLogConsumer {
    static final String QUEUE = "q.tutorial_five.order.log";

    public static void main(String[] args) {
        MyRabbitMQ.start(OrderLogConsumer.class.getSimpleName(), false, channel -> {
            channel.queueDeclare(QUEUE, false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                System.out.printf("Received message='%s'\n", new String(delivery.getBody()));
            };
            channel.basicConsume(QUEUE, true, deliverCallback, consumerTag -> { });
        });
    }
}
