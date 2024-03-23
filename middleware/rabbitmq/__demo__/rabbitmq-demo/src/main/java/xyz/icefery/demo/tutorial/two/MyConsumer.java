package xyz.icefery.demo.tutorial.two;

import com.rabbitmq.client.DeliverCallback;
import xyz.icefery.demo.util.MyRabbitMQ;
import java.util.concurrent.TimeUnit;

/**
 * 工作队列模式-消费者
 */
public class MyConsumer {
    static final String QUEUE = "q.tutorial_two";

    public static void main(String[] args) {
        String connectionName1 = MyConsumer.class.getSimpleName() + "1";
        String connectionName2 = MyConsumer.class.getSimpleName() + "2";
        long cost1 = 500L;
        long cost2 = 1500L;

        // new Thread(() -> roundRobinDispatch(connectionName1, cost1)).start();
        // new Thread(() -> roundRobinDispatch(connectionName2, cost2)).start();

        new Thread(() -> fairDispatch(connectionName1, cost1)).start();
        new Thread(() -> fairDispatch(connectionName2, cost2)).start();

        while (true) { }
    }

    /**
     * 工作队列模式-轮询分发
     */
    static void roundRobinDispatch(String connectionName, long cost) {
        MyRabbitMQ.start(connectionName, false, channel -> {
            channel.queueDeclare(QUEUE, false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                // 1. 收到消息
                System.out.printf("Received consumer='%s' message='%s'\n", connectionName, new String(delivery.getBody()));
                // 2. 处理消息
                try { TimeUnit.MILLISECONDS.sleep(cost); } catch (Exception ignored) { }
            };
            channel.basicConsume(QUEUE, true, deliverCallback, consumerTag -> { });
        });
    }

    /**
     * 工作队列模式-公平分发
     */
    static void fairDispatch(String connectionName, long cost) {
        MyRabbitMQ.start(connectionName, false, channel -> {
            channel.queueDeclare(QUEUE, false, false, false, null);
            channel.basicQos(1); // 限制未确认消息缓冲区大小(一次只接收一条未确认的消息)
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                // 1. 收到消息
                System.out.printf("Received consumer='%s' message='%s'\n", connectionName, new String(delivery.getBody()));

                // 2. 处理消息
                try { TimeUnit.MILLISECONDS.sleep(cost); } catch (Exception ignored) { }

                long deliveryTag = delivery.getEnvelope().getDeliveryTag(); // 消息 ID(服务器端向消费者推送消息，消息会携带一个 `deliveryTag` 参数，也可以称此参数为消息的唯一标识，是一个递增的正整数)
                boolean multiple = false;                                   // 是否批量应答(如当前 tag=8，并且通道上 tag 在 5-8 的消息还未应答，是否一次性应答当前 tag 及其之前所有尚未应答的消息)
                boolean requeue = false;                                    // 是否重新入队

                // 3. 手动应答(肯定确认)(告知 RabbitMQ 该消息被成功处理了)
                channel.basicAck(deliveryTag, multiple);

                // // 3. 手动应答(否定确认)(不处理该消息直接拒绝)
                // channel.basicNack(deliveryTag, multiple, requeue);
            };
            channel.basicConsume(QUEUE, false, deliverCallback, consumerTag -> { });
        });
    }
}
