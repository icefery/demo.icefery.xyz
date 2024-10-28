package xyz.icefery.demo.tutorial.five;

import com.rabbitmq.client.DeliverCallback;
import xyz.icefery.demo.util.MyRabbitMQ;

/**
 * 主题模式-收集库存服务日志消费者
 */
public class StockLogConsumer {

    static final String QUEUE = "q.tutorial_five.stock.log";

    public static void main(String[] args) {
        MyRabbitMQ.start(StockLogConsumer.class.getSimpleName(), false, channel -> {
            channel.queueDeclare(QUEUE, false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                System.out.printf("Received message='%s'\n", new String(delivery.getBody()));
            };
            channel.basicConsume(QUEUE, true, deliverCallback, consumerTag -> {});
        });
    }
}
