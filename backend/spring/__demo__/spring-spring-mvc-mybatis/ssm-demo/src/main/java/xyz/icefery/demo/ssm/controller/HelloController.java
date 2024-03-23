package xyz.icefery.demo.ssm.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HelloController {
    @RequestMapping("/hello")
    public String hello(@RequestParam String name) {
        log.trace("TRACE 日志输出 {}", name);
        log.debug("DEBUG 日志输出 {}", name);
        log.info("INFO 日志输出 {}", name);
        log.warn("WARN 日志输出 {}", name);
        log.error("ERROR 日志输出 {}", name);
        return name;
    }
}
