package xyz.xgh.questionnaire.questionnaire.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;

public class HttpServletUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> void responseJson(HttpServletResponse response, T data) throws IOException {
        String json = OBJECT_MAPPER.writeValueAsString(data);
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(json);
    }
}
