package xyz.icefery.demo.intercerceptor;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TypeInterceptor implements Interceptor {
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
        // 根据 body 中是否含有 "atguigu" 来决定添加怎样的头信息
        String type = body.contains("atguigu") ? "first" : "second";
        headers.put("type", type);
        return event;
    }

    // ；批量事件拦截
    @Override
    public List<Event> intercept(List<Event> events) {
        eventList.clear();
        for (Event event : events) {
            eventList.add(intercept(event));
        }
        return eventList;
    }

    @Override
    public void close() { }

    public static class Builder implements Interceptor.Builder {
        @Override
        public Interceptor build() {
            return new TypeInterceptor();
        }

        @Override
        public void configure(Context context) { }
    }
}
