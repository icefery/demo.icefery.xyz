package xyz.icefery.demo.tutorial.four;

import com.rabbitmq.client.BuiltinExchangeType;
import xyz.icefery.demo.util.MyRabbitMQ;
import java.util.List;

/**
 * 路由模式-生产者
 */
public class MyProducer {
    static final String EXCHANGE = "x.tutorial_four.log.direct";
    static final String QUEUE_LOG = LogConsumer.QUEUE;
    static final String QUEUE_ERROR_LOG = ErrorLogConsumer.QUEUE;
    static final String ROUTING_KEY_INFO = "info";
    static final String ROUTING_KEY_WARN = "warn";
    static final String ROUTING_KEY_ERROR = "error";

    static final List<String> MESSAGE_LIST = List.of(
        "[2022-01-01 00:00:00.100][main      ][info ] This is an info log.",
        "[2022-01-01 00:00:00.200][main      ][warn ] This is a warn log.",
        "[2022-01-01 00:00:00.300][main      ][error] This is a error log."
    );

    public static void main(String[] args) {
        MyRabbitMQ.start(MyProducer.class.getSimpleName(), true, channel -> {
            // 1. 声明交换机
            channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.DIRECT, false);
            // 2. 声明队列
            channel.queueDeclare(QUEUE_LOG, false, false, false, null);
            channel.queueDeclare(QUEUE_ERROR_LOG, false, false, false, null);
            // 3. 绑定交换机和队列
            channel.queueBind(QUEUE_LOG, EXCHANGE, ROUTING_KEY_INFO);
            channel.queueBind(QUEUE_LOG, EXCHANGE, ROUTING_KEY_WARN);
            channel.queueBind(QUEUE_LOG, EXCHANGE, ROUTING_KEY_ERROR);
            channel.queueBind(QUEUE_ERROR_LOG, EXCHANGE, ROUTING_KEY_ERROR);
            // 4. 发送消息
            for (String body : MESSAGE_LIST) {
                String routingKey = body.substring(38, 43).trim();
                channel.basicPublish(EXCHANGE, routingKey, null, body.getBytes());
                System.out.printf("Sent routingKey='%s' message='%s'\n", routingKey, body);
            }
        });
    }
}
