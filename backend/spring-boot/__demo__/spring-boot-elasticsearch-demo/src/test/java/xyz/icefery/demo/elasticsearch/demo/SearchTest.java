package xyz.icefery.demo.elasticsearch.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.icefery.demo.elasticsearch.demo.service.HeroService;
import java.util.Map;

@Slf4j
@SpringBootTest
public class SearchTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private HeroService heroService;

    @Test
    void highlightSearch() throws JsonProcessingException {
        Map<String, Object> map = heroService.hightlightSearch("无双剑姬 菲奥娜");
        String json = objectMapper.writeValueAsString(map);
        log.info(json);
    }
}
