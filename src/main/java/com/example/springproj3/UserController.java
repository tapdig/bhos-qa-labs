package com.example.springproj3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public String saveUser(@RequestBody User user) throws ExecutionException, InterruptedException {
        return userService.addUser(user);
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable String id) throws ExecutionException, InterruptedException {
        return userService.readUser(id);
    }
}