package xyz.icefery.demo.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import xyz.icefery.demo.config.RabbitMQConfig;
import java.util.List;

@Component
public class BatchConsumer {
    @RabbitListener(queues = { RabbitMQConfig.QUEUE }, containerFactory = "batchConsumeContainerFactory")
    public void onMessage(List<Message> messages, Channel channel) {
        for (Message message : messages) {
            System.out.printf("Received raw='%s'\n", new String(message.getBody()));
        }
    }
}
