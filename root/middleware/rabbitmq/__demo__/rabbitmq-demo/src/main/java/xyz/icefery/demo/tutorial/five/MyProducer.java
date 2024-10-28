package xyz.icefery.demo.tutorial.five;

import com.rabbitmq.client.BuiltinExchangeType;
import java.util.List;
import xyz.icefery.demo.util.MyRabbitMQ;

/**
 * 主题模式-生产者
 */
public class MyProducer {

    static final String EXCHANGE = "x.tutorial_five.topic";
    static final String QUEUE_ORDER_LOG = OrderLogConsumer.QUEUE;
    static final String QUEUE_STOCK_LOG = StockLogConsumer.QUEUE;
    // 主题(主题是由多个单词以 `.` 隔开的路由键，`*` 表示有且仅有一个单词，`#` 表示有任意个单词)
    static final String TOPIC_ORDER = "order.#.log.*";
    static final String TOPIC_STOCK = "stock.#.log.*";

    static final List<String> MESSAGE_LIST = List.of(
        "-rw-r--r-- 1 root root 1M 2022-01-01 00:00 order.info.log.2022-01-01", // 匹配订单主题
        "-rw-r--r-- 1 root root 1K 2022-01-01 00:00 order.warn.log.2022-01-01", // 匹配订单主题
        "-rw-r--r-- 1 root root 10 2022-01-01 00:00 order.error.log.2022-01-01", // 匹配订单主题
        "-rw-r--r-- 1 root root  0 2022-01-01 00:00 stock.log",
        "-rw-r--r-- 1 root root 1M 2022-01-01 00:00 stock.log.2022-01-01" // 匹配库存主题
    );

    public static void main(String[] args) {
        MyRabbitMQ.start(MyProducer.class.getSimpleName(), true, channel -> {
            channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.TOPIC, false);
            channel.queueDeclare(QUEUE_ORDER_LOG, false, false, false, null);
            channel.queueDeclare(QUEUE_STOCK_LOG, false, false, false, null);
            channel.queueBind(QUEUE_ORDER_LOG, EXCHANGE, TOPIC_ORDER);
            channel.queueBind(QUEUE_STOCK_LOG, EXCHANGE, TOPIC_STOCK);
            for (String body : MESSAGE_LIST) {
                // ls -AFlh --time-style=long-iso | awk '{ print $8 }'
                String[] split = body.split(" ");
                if (split.length == 8) {
                    String routingKey = split[7];
                    channel.basicPublish(EXCHANGE, routingKey, null, body.getBytes());
                    System.out.printf("sent routingKey='%s' message='%s'\n", routingKey, body);
                }
            }
        });
    }
}
