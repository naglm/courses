package com.example.courses.service;

import com.example.courses.model.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class QuoteServiceImpl implements QuoteService {

    private static final Logger log = LoggerFactory.getLogger(QuoteServiceImpl.class);

    private RestTemplate restTemplate;

    public QuoteServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Quote getRandomQuote() {
        return restTemplate.getForObject("https://gturnquist-quoters.cfapps.io/api/random", Quote.class);

    }
}
