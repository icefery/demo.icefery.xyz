package xyz.icefery.demo.elasticsearch.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping({ "/", "/index", "index.html" })
    public String index() {
        return "index";
    }
}
