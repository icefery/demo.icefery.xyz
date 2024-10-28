package xyz.icefery.demo.dead_letter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import xyz.icefery.demo.util.MyRabbitMQ;

/**
 * 死信队列-生产者
 */
public class MyProducer {

    static final String NORMAL_EXCHANGE = NormalConsumer.EXCHANGE;
    static final String NORMAL_ROUTING_KEY = NormalConsumer.ROUTING_KEY;

    static List<String> MESSAGE_LIST = new ArrayList<>();

    static {
        for (int i = 0; i < 10; i++) {
            MESSAGE_LIST.add(i + "");
        }
        for (int i = 0; i < 10; i++) {
            MESSAGE_LIST.add((char) (i + 65) + "");
        }
        Collections.shuffle(MESSAGE_LIST);
    }

    public static void main(String[] args) {
        MyRabbitMQ.start(MyProducer.class.getSimpleName(), true, channel -> {
            for (String message : MESSAGE_LIST) {
                String body = message + " " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                channel.basicPublish(NORMAL_EXCHANGE, NORMAL_ROUTING_KEY, null, body.getBytes());
                System.out.printf("Sent message='%s'\n", body);
                TimeUnit.MILLISECONDS.sleep(1000);
            }
        });
    }
}
