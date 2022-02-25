package xyz.icefery.demo.source;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.PollableSource;
import org.apache.flume.conf.Configurable;
import org.apache.flume.event.SimpleEvent;
import org.apache.flume.source.AbstractSource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MySource extends AbstractSource implements Configurable, PollableSource {
    private Long delay;
    private String field;

    @Override
    public void configure(Context context) {
        delay = context.getLong("delay");
        field = context.getString("field");
    }

    @Override
    public Status process() throws EventDeliveryException {
        try {
            Map<String, String> headers = new HashMap<>();
            Event event = new SimpleEvent();
            for (int i = 0; i < 5; i++) {
                event.setHeaders(headers);
                event.setBody((field + i).getBytes());
                getChannelProcessor().processEvent(event);
                TimeUnit.MILLISECONDS.sleep(delay);
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
