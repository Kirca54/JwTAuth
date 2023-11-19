package com.example.dt_2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class InitialController {

    @GetMapping("/")
    @ResponseBody
    public String helloWorld() {
        return "Hello World!";
    }
}
