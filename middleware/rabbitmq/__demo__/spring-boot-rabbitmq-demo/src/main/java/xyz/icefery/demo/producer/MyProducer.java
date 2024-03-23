package xyz.icefery.demo.producer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.BatchingRabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.icefery.demo.config.RabbitMQConfig;
import xyz.icefery.demo.message.MyMessage;

@Component
public class MyProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private BatchingRabbitTemplate batchingRabbitTemplate;

    public void sendRaw(String raw) {
        MessageProperties properties = MessagePropertiesBuilder
            .newInstance()
            .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
            .build();
        Message message = MessageBuilder
            .withBody(raw.getBytes())
            .andProperties(properties)
            .build();
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, message);
    }

    public void sendObject(MyMessage object) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, object);
    }

    public void batchingSendRaw(String raw) {
        Message message = MessageBuilder.withBody(raw.getBytes()).build();
        batchingRabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, message);
    }
}
