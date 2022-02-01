package xyz.icefery.demo.util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


public class MyRabbitMQ {
    private static final ConnectionFactory CONNECTION_FACTORY = new ConnectionFactory();

    static {
        CONNECTION_FACTORY.setHost("192.192.192.6");
        CONNECTION_FACTORY.setPort(5672);
        CONNECTION_FACTORY.setUsername("admin");
        CONNECTION_FACTORY.setPassword("admin");
        CONNECTION_FACTORY.setVirtualHost("/");
    }

    public static void start(String connectionName, boolean autoClose, ThrowableConsumer<Channel, Exception> throwableConsumer) {
        if (autoClose) {
            try (
                Connection connection = CONNECTION_FACTORY.newConnection(connectionName);
                Channel channel = connection.createChannel()
            ) {
                throwableConsumer.accept(channel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                Connection connection = CONNECTION_FACTORY.newConnection(connectionName);
                Channel channel = connection.createChannel();
                throwableConsumer.accept(channel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static interface ThrowableConsumer<T, E extends Throwable> {
        void accept(T t) throws E;
    }
}
