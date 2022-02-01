package xyz.icefery.demo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.batch.SimpleBatchingStrategy;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.BatchingRabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@Configuration
public class RabbitMQConfig {
    public static final String EXCHANGE = "x.spring_boot.direct";
    public static final String QUEUE = "q.spring_boot";
    public static final String ROUTING_KEY = "";

    // 消息序列化
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue springBootQueue() {
        // boolean durable = false;
        // boolean exclusive = false;
        // boolean autoDelete = false;
        // Map<String, Object> arguments = null;
        // Queue queue = new Queue(QUEUE, durable, exclusive, autoDelete, arguments);
        return QueueBuilder
            .nonDurable(QUEUE)
            .build();
    }

    @Bean
    public Exchange springBootExchange() {
        // boolean durable = false;
        // boolean autoDelete = false;
        // Map<String, Object> arguments = null;
        // DirectExchange exchange = new DirectExchange(EXCHANGE, durable, autoDelete, arguments);
        return ExchangeBuilder
            .directExchange(EXCHANGE)
            .build();
    }

    @Bean
    public Binding binding1(@Qualifier("springBootQueue") Queue queue, @Qualifier("springBootExchange") Exchange exchange) {
        // Map<String, Object> arguments = null;
        // Binding binding = new Binding(QUEUE, Binding.DestinationType.QUEUE, EXCHANGE, ROUTING_KEY, arguments);
        return BindingBuilder
            .bind(queue)
            .to(exchange)
            .with(ROUTING_KEY)
            .noargs();
    }

    @Bean
    public BatchingRabbitTemplate batchingRabbitTemplate(ConnectionFactory connectionFactory) {
        // 批量策略
        int batchSize = 100;                // 批量收集最大消息条数
        int bufferLimit = 16 * 1024 * 1024; // 批量发送最大内存
        long timeout = 60 * 1000L;          // 批量收集最长等待时间
        SimpleBatchingStrategy batchingStrategy = new SimpleBatchingStrategy(batchSize, bufferLimit, timeout);
        // 超时发送定时器
        TaskScheduler taskScheduler = new ConcurrentTaskScheduler();

        BatchingRabbitTemplate batchingRabbitTemplate = new BatchingRabbitTemplate(connectionFactory, batchingStrategy, taskScheduler);
        batchingRabbitTemplate.setMessageConverter(jsonMessageConverter());
        return batchingRabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory batchConsumeContainerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);

        factory.setMessageConverter(jsonMessageConverter());
        // 启用批量消费
        factory.setBatchListener(true);
        factory.setConsumerBatchEnabled(true);
        // 阻塞等待最多 receiveTimeout 毫秒 拉取 batchSize 条消息进行消费
        factory.setBatchSize(100);
        factory.setReceiveTimeout(60 * 1000L);
        return factory;
    }

    @Bean
    public RabbitTransactionManager rabbitTransactionManager(ConnectionFactory connectionFactory, RabbitTemplate rabbitTemplate) {
        rabbitTemplate.setChannelTransacted(true);
        return new RabbitTransactionManager(connectionFactory);
    }
}
