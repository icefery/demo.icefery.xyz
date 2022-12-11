package xyz.icefery.demo.tutorial.one;


import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 简单模式-消费者
 */
public class MyConsumer {
    public static void main(String[] args) {
        // 1. 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.192.192.6");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");

        // 不使用 try-with-resources 语法(避免消费完消息后连接自动释放)
        try {
            // 2. 创建连接
            Connection connection = factory.newConnection(MyConsumer.class.getSimpleName());

            // 3. 创建通道
            Channel channel = connection.createChannel();

            String queue = "q.tutorial_one";

            // 4. 声明队列(声明一个队列是幂等的，只有当它不存在时才会创建)
            channel.queueDeclare(queue, false, false, false, null);

            // 5. 消费消息
            boolean autoAck = true;                                        // 是否自动应答（默认为 false）
            DeliverCallback deliverCallback = (consumerTag, delivery) -> { // 消费回调
                String body = new String(delivery.getBody());
                System.out.printf("Received message='%s'\n", body);
            };
            CancelCallback cancelCallback = consumerTag -> {               // 取消消费回调(如在消费时队列被删除)
                System.err.println("Consuming was interrupted");
            };
            channel.basicConsume(queue, autoAck, deliverCallback, cancelCallback);
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
