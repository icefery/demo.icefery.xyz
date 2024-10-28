package xyz.icefery.demo.elasticsearch.demo.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import xyz.icefery.demo.elasticsearch.demo.service.HeroService;

@RestController
public class HeroController {

    @Autowired
    private HeroService heroService;

    @GetMapping(value = "/highlight-search/{keyword}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> highlightSearch(@PathVariable("keyword") String keyword) {
        return heroService.hightlightSearch(keyword);
    }
}
