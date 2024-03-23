package xyz.icefery.demo.tutorial.two;

import xyz.icefery.demo.util.MyRabbitMQ;

/**
 * 工作队列模式-生产者
 */
public class MyProducer {
    static final String EXCHANGE = "";            // 默认交换机
    static final String QUEUE = MyConsumer.QUEUE; // 队列一般在消费者端声明

    public static void main(String[] args) {
        MyRabbitMQ.start(MyProducer.class.getSimpleName(), true, channel -> {
            channel.queueDeclare(QUEUE, false, false, false, null);
            for (int i = 1; i <= 20; i++) {
                String body = "This is the " + i + "-th message";
                channel.basicPublish(EXCHANGE, QUEUE, null, body.getBytes());
                System.out.printf("Sent message='%s'\n", body);
            }
        });
    }
}
