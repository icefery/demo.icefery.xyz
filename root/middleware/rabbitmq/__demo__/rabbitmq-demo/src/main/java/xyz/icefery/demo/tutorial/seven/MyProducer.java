package xyz.icefery.demo.tutorial.seven;

import com.rabbitmq.client.ConfirmListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import xyz.icefery.demo.util.MyRabbitMQ;

/**
 * 发布确认模式-生产者
 */
public class MyProducer {

    static final String EXCHANGE = "";

    static final List<String> MESSAGE_LIST = new ArrayList<>();

    static {
        for (int i = 1; i <= 5000; i++) {
            MESSAGE_LIST.add("msg" + i);
        }
    }

    public static void main(String[] args) {
        noConfirm();
        confirmIndividuallySync();
        confirmBatchSync();
        confirmAsync();
    }

    /**
     * 无确认
     */
    private static void noConfirm() {
        String mode = "无确认";
        MyRabbitMQ.start(MyProducer.class.getSimpleName(), true, channel -> {
            String queue = channel.queueDeclare().getQueue(); // 创建临时队列(主动声明一个由服务端命名的、排它的、自动删除的、非持久化的队列)
            long start = System.currentTimeMillis();
            for (String body : MESSAGE_LIST) {
                channel.basicPublish(EXCHANGE, queue, null, body.getBytes());
            }
            long end = System.currentTimeMillis();
            System.out.printf("[%-20s] Sent %d messages for %d ms\n", mode, MESSAGE_LIST.size(), end - start);
        });
    }

    /**
     * 单独同步确认
     */
    private static void confirmIndividuallySync() {
        String mode = "单独同步确认";
        MyRabbitMQ.start(MyProducer.class.getSimpleName(), true, channel -> {
            String queue = channel.queueDeclare().getQueue();
            channel.confirmSelect(); // 启用发布确认
            long start = System.currentTimeMillis();
            for (String body : MESSAGE_LIST) {
                channel.basicPublish(EXCHANGE, queue, null, body.getBytes());
                channel.waitForConfirmsOrDie(1000L); // 等待确认
            }
            long end = System.currentTimeMillis();
            System.out.printf("[%-20s] Sent %d messages for %d ms\n", mode, MESSAGE_LIST.size(), end - start);
        });
    }

    /**
     * 批量同步确认
     */
    private static void confirmBatchSync() {
        String mode = "批量同步确认";
        MyRabbitMQ.start(MyProducer.class.getSimpleName(), true, channel -> {
            String queue = channel.queueDeclare().getQueue();
            channel.confirmSelect(); // 启用发布确认
            long batchSize = 100L; // 批次大小
            long outstandingMessageCount = 0L; // 未确认的消息数
            long start = System.currentTimeMillis();
            for (String body : MESSAGE_LIST) {
                channel.basicPublish(EXCHANGE, queue, null, body.getBytes());
                outstandingMessageCount++;
                if (outstandingMessageCount == batchSize) { // 批量等待确认(当未确认的消息数到达批次大小时再等待确认)
                    channel.waitForConfirmsOrDie(1000L);
                    outstandingMessageCount = 0L;
                }
            }
            if (outstandingMessageCount > 0) { // 批量等待确认(消息全部发送后可能仍有未确认的消息)
                channel.waitForConfirmsOrDie(1000L);
                outstandingMessageCount = 0L;
            }
            long end = System.currentTimeMillis();
            System.out.printf("[%-20s] Sent %d messages for %d ms\n", mode, MESSAGE_LIST.size(), end - start);
        });
    }

    /**
     * 异步确认
     */
    private static void confirmAsync() {
        String mode = "异步确认";
        MyRabbitMQ.start(MyProducer.class.getSimpleName(), true, channel -> {
            String queue = channel.queueDeclare().getQueue();
            channel.confirmSelect(); // 启用发布确认
            channel.addConfirmListener(
                new ConfirmListener() { // 确认回调
                    @Override
                    public void handleAck(long deliveryTag, boolean multiple) throws IOException {}

                    @Override
                    public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                        System.out.println("Publishing failed");
                    }
                }
            );
            long start = System.currentTimeMillis();
            for (String body : MESSAGE_LIST) {
                channel.basicPublish(EXCHANGE, queue, null, body.getBytes());
            }
            long end = System.currentTimeMillis();
            System.out.printf("[%-20s] Sent %d messages for %d ms\n", mode, MESSAGE_LIST.size(), end - start);
        });
    }
}
