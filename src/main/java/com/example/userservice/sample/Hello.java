package com.example.userservice.sample;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello {
    @GetMapping("/hello")
    public String hello() {
        System.out.println("hello from product service");
        return "Hello World from user service";
    }
}
