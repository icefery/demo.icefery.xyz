package xyz.icefery.demo.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    // 在权限表中 | 访问资源需要角色 ROLE_1
    @GetMapping("/hello1")
    public String hello1() {
        return "hello1";
    }

    // 在权限表中 | 访问资源需要角色 ROLE_2
    @GetMapping("/hello2")
    public String hello2() {
        return "hello2";
    }

    // 在权限表中 | 访问资源需要角色 ROLE_3
    @GetMapping("/hello3")
    public String hello3() {
        return "hello3";
    }

    // 在权限表中 | 允许匿名访问
    @GetMapping("/hello4")
    public String hello4() {
        return "hello4";
    }

    // 不在权限表中 | 匹配 /hello1/** | 访问资源需要 ROLE_1
    @GetMapping("/hello1/sub")
    public String hello1Sub() {
        return "hello1_sub";
    }

    // 不在权限表中 | 不匹配 /hello3 | 访问资源需要登录
    @GetMapping("/hello3/sub")
    public String hello3Sub() {
        return "hello3_sub";
    }

    // 在权限表中 | 访问资源不需要角色 | 访问资源需要登录
    @GetMapping("/hello5")
    public String hello5() {
        return "Hello5";
    }
}
