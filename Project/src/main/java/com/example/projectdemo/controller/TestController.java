package com.example.projectdemo.controller;

import com.example.projectdemo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private TestService testService;

    @GetMapping("/test/{id}")
    public String test(@PathVariable int id) {
        return testService.test(id);
    }
}
