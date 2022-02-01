package xyz.icefery.demo.tutorial.four;

import com.rabbitmq.client.DeliverCallback;
import xyz.icefery.demo.util.MyRabbitMQ;
import java.util.concurrent.TimeUnit;

/**
 * 路由模式-收集错误日志消费者
 */
public class ErrorLogConsumer {
    static final String QUEUE = "q.tutorial_four.error";

    public static void main(String[] args) {
        MyRabbitMQ.start(ErrorLogConsumer.class.getSimpleName(), false, channel -> {
            channel.queueDeclare(QUEUE, false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                // 1. 收到消息
                System.out.printf("Received message='%s'\n", new String(delivery.getBody()));
                // 2. 处理消息（告警）
                try { TimeUnit.MILLISECONDS.sleep(500L); } catch (InterruptedException ignored) { }
                // 3. 手动应答
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            };
            channel.basicConsume(QUEUE, false, deliverCallback, consumerTag -> { });
        });
    }
}
