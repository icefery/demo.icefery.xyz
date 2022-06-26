package xyz.icefery.demo.ssm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.icefery.demo.ssm.entity.User;
import xyz.icefery.demo.ssm.service.UserService;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/select/{id}")
    public User selectUserById(@PathVariable Long id) {
        return userService.selectUserById(id);
    }

    @PutMapping("/update")
    public void updateUserById(@RequestBody User user) {
        userService.updateUserById(user);
    }
}