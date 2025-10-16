package com.steve.SpringAI_System_Role.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api")
public class ChatStreamController {

    private final ChatClient chatClient;

    public ChatStreamController(@Qualifier("openaiChatClient") ChatClient chatClientBuilder) {
        this.chatClient = chatClientBuilder;
    }

    @GetMapping("/stream")
    public Flux<String> stream(String message) {
        return chatClient
                .prompt()
                .user(message)
                .stream()
                .content();
    }

}
