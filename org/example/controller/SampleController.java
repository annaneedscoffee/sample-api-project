package org.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.example.loggingstarter.service.LoggingService;


@RestController
@RequestMapping("/api/v1")
public class SampleController {

    @Autowired
    private final LoggingService loggingService;

    public SampleController(final LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    @GetMapping(value = "/greet", produces = MediaType.APPLICATION_JSON_VALUE)
    public String greet() {
        return "Hello, World!";
    }

    @PostMapping(value = "/echo", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String echo(@RequestBody String message) {
        loggingService.log(message);
        return message;
    }
}
