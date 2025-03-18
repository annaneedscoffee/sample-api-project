package org.example.service;


import org.example.kafka.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class SampleService {

    @Autowired
    private MessageProducer messageProducer;

    public String echo(String message) throws ExecutionException, InterruptedException {
        List<String> kafkaConsumerUrls = Arrays.asList("localhost:9093", "localhost:9094");

        List<CompletableFuture<String>> futures = kafkaConsumerUrls.stream()
                .map(kafkaConsumerUrl -> CompletableFuture.supplyAsync(() -> sendMessageToAnotherService(kafkaConsumerUrl, message)))
                .toList();

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

        allFutures.get();

        List<String> results = futures.stream()
                .map(CompletableFuture::join)
                .toList();

        return results.toString();
    }

    String sendMessageToAnotherService(String kafkaConsumerUrl, String message) {
        messageProducer.sendMessage("my-topic", message);
        return "Message sent: " + message;
    };


}
