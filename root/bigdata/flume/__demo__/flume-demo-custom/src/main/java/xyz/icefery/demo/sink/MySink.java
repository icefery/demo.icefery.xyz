package xyz.icefery.demo.sink;

import org.apache.flume.Channel;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.Transaction;
import org.apache.flume.conf.Configurable;
import org.apache.flume.sink.AbstractSink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MySink extends AbstractSink implements Configurable {

    private static final Logger log = LoggerFactory.getLogger(AbstractSink.class);

    private String prefix;
    private String suffix;

    @Override
    public void configure(Context context) {
        prefix = context.getString("prefix");
        suffix = context.getString("suffix");
    }

    @Override
    public Status process() throws EventDeliveryException {
        Channel channel = getChannel();
        Transaction tx = channel.getTransaction();
        tx.begin();
        // 读取 Channel 中的事件
        Event event;
        while (true) {
            event = channel.take();
            if (event != null) {
                break;
            }
        }
        // 处理事件
        Status status;
        try {
            log.info(prefix + new String(event.getBody()) + suffix);
            tx.commit();
            status = Status.READY;
        } catch (Exception e) {
            tx.rollback();
            status = Status.BACKOFF;
        } finally {
            tx.close();
        }
        return status;
    }
}
