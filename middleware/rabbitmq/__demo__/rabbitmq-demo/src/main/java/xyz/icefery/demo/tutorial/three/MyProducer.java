package xyz.icefery.demo.tutorial.three;

import com.rabbitmq.client.BuiltinExchangeType;
import xyz.icefery.demo.util.MyRabbitMQ;

/**
 * 发布订阅模式-生产者
 */
public class MyProducer {
    static final String EXCHANGE = "x.tutorial_three.fanout";
    static final String QUEUE1 = MyConsumer1.QUEUE;
    static final String QUEUE2 = MyConsumer2.QUEUE;
    static final String ROUTING_KEY = "";

    public static void main(String[] args) {
        MyRabbitMQ.start(MyProducer.class.getSimpleName(), true, channel -> {
            // 1. 声明交换机
            channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.FANOUT, false);
            // 2. 声明队列
            channel.queueDeclare(QUEUE1, false, false, false, null);
            channel.queueDeclare(QUEUE2, false, false, false, null);
            // 3. 绑定交换机和队列
            channel.queueBind(QUEUE1, EXCHANGE, ROUTING_KEY);
            channel.queueBind(QUEUE2, EXCHANGE, ROUTING_KEY);
            // 4. 发送消息
            String body = "This is a broadcast message";
            channel.basicPublish(EXCHANGE, ROUTING_KEY, null, body.getBytes());
            System.out.printf("Sent message='%s'\n", body);
        });
    }
}
