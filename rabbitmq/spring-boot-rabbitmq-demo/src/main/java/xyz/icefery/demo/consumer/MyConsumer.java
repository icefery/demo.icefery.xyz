package xyz.icefery.demo.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import xyz.icefery.demo.config.RabbitMQConfig;
import xyz.icefery.demo.message.MyMessage;

@Component
public class MyConsumer {
    @RabbitListener(queues = { RabbitMQConfig.QUEUE })
    public void onMessage(Message message, Channel channel) {
        System.out.printf("Received raw='%s'\n", new String(message.getBody()));
    }

    @RabbitListener(queues = { RabbitMQConfig.QUEUE })
    public void handleMessage(MyMessage object, Channel channel, Message message) {
        System.out.printf("Received object='%s' raw='%s'\n", object, new String(message.getBody()));
    }
}
