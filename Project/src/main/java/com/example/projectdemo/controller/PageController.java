package com.example.projectdemo.controller;

import com.example.projectdemo.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author Zero
 */
@Controller
public class PageController {
    @ApiIgnore
    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @ApiIgnore
    @GetMapping("/subject")
    public String subject() {
        return "subject";
    }

    @ApiIgnore
    @GetMapping("/select")
    public String select() {
        return "select";
    }
}
