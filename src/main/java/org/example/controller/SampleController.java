package org.example.controller;

import io.micrometer.common.util.StringUtils;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.example.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;


@RestController
@RequestMapping("/api/v1")
public class SampleController {

    @Autowired
    private SampleService sampleService;

    @Autowired
    private MeterRegistry meterRegistry;

    @GetMapping(value = "/greet", produces = MediaType.APPLICATION_JSON_VALUE)
    public String greet() {
        return "Hello, World!";
    }

    @PostMapping(value = "/echo", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String echo(@RequestBody String message) throws ExecutionException, InterruptedException {
        Counter counter = Counter.builder("api_echo_calls")
                .description("a number of requests to api/v1/echo")
                .register(meterRegistry);
        counter.increment();
        return sampleService.echo(message);
    }
}
