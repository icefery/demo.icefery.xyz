package xyz.icefery.demo.cloud.component.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import xyz.icefery.demo.cloud.microservice.util.R;

@Component
public class IEFilter extends ZuulFilter {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        HttpServletResponse response = context.getResponse();

        String userAgent = request.getHeader("User-Agent");
        if (userAgent != null && userAgent.contains("Trident")) {
            context.setSendZuulResponse(false);
            R<Object> r = R.failure(501, "IE is not supported");
            try {
                String json = objectMapper.writeValueAsString(r);
                response.getWriter().write(json);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
