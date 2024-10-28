package xyz.icefery.demo.consumer;

import com.rabbitmq.client.Channel;
import java.util.List;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import xyz.icefery.demo.config.RabbitMQConfig;

@Component
public class BatchConsumer {

    @RabbitListener(queues = { RabbitMQConfig.QUEUE }, containerFactory = "batchConsumeContainerFactory")
    public void onMessage(List<Message> messages, Channel channel) {
        for (Message message : messages) {
            System.out.printf("Received raw='%s'\n", new String(message.getBody()));
        }
    }
}
