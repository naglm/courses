package com.example.courses.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("coding")
public class CodingController {

    private static final String SEPARATOR = " ";

    Map<Integer, Integer> cache = new HashMap<>();

    /**
     *
     * @param max - maximum number
     * @return fibonacc string
     */
    @GetMapping(path = "/fibonacci")
    public String fibonacci(@RequestParam(required = false) Optional<Integer> max) {
        int a = 1;
        int b = 1;
        int current = a + b;

        int maxValue = max.isPresent() ? max.get() : 1000;

        StringBuilder result = new StringBuilder();
        result.append(a).append(SEPARATOR);
        result.append(b).append(SEPARATOR);

        while(current < maxValue) {
            result.append(current).append(SEPARATOR);
            a = b;
            b = current;
            current = a + b;
        }

        return result.toString();
    }


    @GetMapping(path = "/fibonacciRec")
    public String fibonacciRecursive(@RequestParam(required = false) Optional<Integer> max) {
        int maxValue = max.isPresent() ? max.get() : 10;
        StringBuilder result = new StringBuilder();

        for(int i = 1; i <= maxValue; i++) {
            result.append(computeFibonacciRec(i) + " ");
        }

        return result.toString();
    }

    private int computeFibonacciRec(int number) {
        if(number == 1 || number == 2) {
            return 1;
        }

        if(cache.containsKey(number)) {
            return cache.get(number);
        }

        int result = computeFibonacciRec(number - 1) + computeFibonacciRec(number - 2);
        cache.put(number, result);
        return result;
    }

    @GetMapping(path = "/fact/{i}")
    public String fact(@PathVariable Integer i) {
        return Integer.toString(factorial(i));
    }

    private int factorial(int i) {
        if(i == 1 ) {
            return 1;
        }
        return i * factorial(i - 1);
    }


}
