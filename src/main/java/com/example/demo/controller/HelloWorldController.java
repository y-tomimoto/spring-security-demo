package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("/hello")
    String hello() {
        return "Hello";
    }

    @GetMapping("/world")
    String world() {
        return "World";
    }

}
