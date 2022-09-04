package xyz.icefery.demo.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import xyz.icefery.demo.config.RabbitMQConfig;

@Component
public class ConcurrencyConsumer {
    @RabbitListener(queues = { RabbitMQConfig.QUEUE }, concurrency = "2")
    public void onMessage(Message message, Channel channel) {
        long thread = Thread.currentThread().getId();
        System.out.printf("Received thread='%d' raw='%s'\n", thread, new String(message.getBody()));
    }
}
