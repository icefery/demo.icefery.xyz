package xyz.icefery.demo.sso.client2.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Client2Controller {

    @GetMapping("/user")
    public String user() {
        return "client2-user";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String admin() {
        return "client2-admin";
    }
}
