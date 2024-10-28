package xyz.icefery.demo.tutorial.one;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * 简单模式-生产者
 */
public class MyProducer {

    public static void main(String[] args) {
        // 1. 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.192.192.6");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");

        // 使用 try-with-resources 语法(发送完消息后连接自动释放)
        String connectionName = MyProducer.class.getSimpleName();
        try (
            // 2. 创建连接
            Connection connection = factory.newConnection(connectionName);
            // 3. 创建通道
            Channel channel = connection.createChannel()
        ) {
            // 4. 声明队列(声明一个队列是幂等的，只有当它不存在时才会创建)
            String queue = "q.tutorial_one"; // 队列名
            boolean durable = false; // 是否持久化(重启后队列是否还存在)
            boolean exclusive = false; // 是否独占
            boolean autoDelete = false; // 是否自动删除
            Map<String, Object> arguments = null; // 其它参数
            channel.queueDeclare(queue, durable, exclusive, autoDelete, arguments);

            // 5. 发送消息
            String exchange = ""; // 交换机名(默认交互机名称为 `AMQP default` 类型为 `direct`)
            String routingKey = queue; // 路由键(默认交换机使用队列名匹配队列)
            AMQP.BasicProperties headers = null; // 消息头
            String body = "This is a message"; // 消息体
            channel.basicPublish(exchange, routingKey, headers, body.getBytes());
            System.out.printf("Sent message='%s'\n", body);
            // 5. 循环发送消息
            // Scanner sc = new Scanner(System.in);
            // while (sc.hasNextLine()) {
            //     String body = sc.nextLine();
            //     channel.basicPublish(exchange, routingKey, null, body.getBytes());
            //     System.out.printf("Sent message='%s'\n", body);
            // }
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
