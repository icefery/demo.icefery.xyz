package xyz.icefery.demo.interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

public class MyInterceptor implements Interceptor {

    private List<Event> eventList;

    @Override
    public void initialize() {
        eventList = new ArrayList<>();
    }

    // 单个事件拦截
    @Override
    public Event intercept(Event event) {
        Map<String, String> headers = event.getHeaders();
        String body = new String(event.getBody());
        if (body.contains("info")) {
            headers.put("type", "info");
        } else if (body.contains("error")) {
            headers.put("type", "error");
        }
        return event;
    }

    // 批量事件拦截
    @Override
    public List<Event> intercept(List<Event> events) {
        eventList.clear();
        for (Event event : events) {
            eventList.add(intercept(event));
        }
        return eventList;
    }

    @Override
    public void close() {}

    public static class Builder implements Interceptor.Builder {

        @Override
        public Interceptor build() {
            return new MyInterceptor();
        }

        @Override
        public void configure(Context context) {}
    }
}
