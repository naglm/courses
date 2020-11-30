package com.example.courses.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/upload")
public class UploadController {
    @GetMapping
    public String upload() {
        return "upload";
    }
}
