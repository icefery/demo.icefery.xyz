package xyz.icefery.demo.source;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.PollableSource;
import org.apache.flume.conf.Configurable;
import org.apache.flume.event.SimpleEvent;
import org.apache.flume.source.AbstractSource;

public class MySource extends AbstractSource implements Configurable, PollableSource {

    private String prefix;

    @Override
    public void configure(Context context) {
        prefix = context.getString("prefix");
    }

    @Override
    public Status process() throws EventDeliveryException {
        try {
            Map<String, String> headers = new HashMap<>();
            Event event = new SimpleEvent();
            for (int i = 0; i < 5; i++) {
                event.setHeaders(headers);
                event.setBody((prefix + i).getBytes());
                getChannelProcessor().processEvent(event);
                TimeUnit.MILLISECONDS.sleep(1000);
            }
            return Status.READY;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return Status.BACKOFF;
        }
    }

    @Override
    public long getBackOffSleepIncrement() {
        return 0;
    }

    @Override
    public long getMaxBackOffSleepInterval() {
        return 0;
    }
}
