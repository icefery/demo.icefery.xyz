package xyz.icefery.demo.elasticsearch.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;
import xyz.icefery.demo.elasticsearch.demo.entity.Hero;

@Slf4j
@Service
public class HeroService {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    public Map<String, Object> hightlightSearch(String keyword) {
        Map<String, Object> map = new HashMap<>();
        Query query = new NativeSearchQueryBuilder()
            .withQuery(new MultiMatchQueryBuilder(keyword, "name", "title", "short_bio"))
            .withHighlightBuilder(
                new HighlightBuilder().preTags("<span style='color:red'>").postTags("</span>").field("short_bio").field("title").field("name")
            )
            .withSort(SortBuilders.scoreSort().order(SortOrder.DESC))
            .withPageable(PageRequest.of(0, 10))
            .build();
        SearchHits<Hero> hits = elasticsearchRestTemplate.search(query, Hero.class);
        // 总命中数
        int total = (int) hits.getTotalHits();
        map.put("total", total);

        List<SearchHit<Hero>> hitList = hits.getSearchHits();

        // 本页命中数
        int size = hitList.size();
        map.put("size", size);

        List<Hero> list = new ArrayList<>();

        // 使用高亮字段替换实体字段
        for (SearchHit<Hero> hit : hitList) {
            Hero hero = hit.getContent();
            //
            List<String> highlightShortBioList = hit.getHighlightField("shortBio");
            if (!highlightShortBioList.isEmpty()) {
                String highlightShortBio = String.join("", highlightShortBioList);
                hero.setShortBio(highlightShortBio);
            }
            //
            List<String> highlightTitleList = hit.getHighlightField("title");
            if (!highlightTitleList.isEmpty()) {
                String highlightTitle = String.join("", highlightTitleList);
                hero.setTitle(highlightTitle);
            }
            //
            List<String> highlightNameList = hit.getHighlightField("name");
            if (!highlightNameList.isEmpty()) {
                String highlightName = String.join("", highlightNameList);
                hero.setName(highlightName);
            }
            list.add(hero);
        }
        // 命中单位集合
        map.put("list", list);

        return map;
    }
}
