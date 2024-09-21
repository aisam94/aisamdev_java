package com.aisamdev.aisamdev_java.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping(value = "/api/test")
public class TestController {
    @GetMapping("hello")
    public String hello() {
        return "Hello there!!!";
    }
}