package com.example.courses.controller;

import com.example.courses.service.QuoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/quote")
public class QuoteController {

    private QuoteService quoteService;

    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping(path = "/random")
    public ResponseEntity randomQuote() {
        return ResponseEntity.ok(quoteService.getRandomQuote());
    }
}
