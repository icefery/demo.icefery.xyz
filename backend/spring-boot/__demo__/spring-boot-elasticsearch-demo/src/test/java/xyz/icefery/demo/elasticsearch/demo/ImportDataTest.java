package xyz.icefery.demo.elasticsearch.demo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import xyz.icefery.demo.elasticsearch.demo.entity.Hero;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest
public class ImportDataTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Autowired
    private MongoTemplate mongoTemplate;

    // 导入数据
    @Test
    void importDataTest() {
        final int start = 1;
        final int end = 856;
        List<Hero> list = new ArrayList<>();
        List<Object> rootList = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            try {
                Connection.Response response = Jsoup
                    .connect("https://game.gtimg.cn/images/lol/act/img/js/hero/" + i + ".js")
                    .timeout(30000)
                    // 忽略请求类型, 请求 JSON
                    .ignoreContentType(true)
                    // 忽略 404 等, 使用 statusCode 判断
                    .ignoreHttpErrors(true)
                    .execute();
                if (response.statusCode() == 200) {
                    // wrapper = { hero: { name: '' } }
                    String body = response.body();
                    JsonNode rootNode = objectMapper.readTree(body);

                    // 向 mongodb 存一份
                    Object o = objectMapper.readValue(body, Object.class);
                    rootList.add(o);

                    // hero = { name: '' }
                    JsonNode heroNode = rootNode.get("hero");
                    Hero hero = objectMapper.readValue(heroNode.toPrettyString(), Hero.class);

                    // hero = { id: i, name: '', avatarSrc: '' }
                    String avatarSrc = "https://game.gtimg.cn/images/lol/act/img/champion/" + hero.getAlias() + ".png";
                    hero.setAvatarSrc(avatarSrc);
                    hero.setId(i);

                    log.info(hero.toString());
                    list.add(hero);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mongoTemplate.insert(rootList, "hero_detail");
        elasticsearchRestTemplate.save(list);
    }
}
