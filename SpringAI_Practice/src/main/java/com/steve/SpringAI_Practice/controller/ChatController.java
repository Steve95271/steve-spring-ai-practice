package com.steve.SpringAI_Practice.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(@Qualifier("openaiChatClient") ChatClient chatClientBuilder) {
        this.chatClient = chatClientBuilder;
    }

    @Value("classpath:/promptTemplates/systemPromptTemplate.st")
    Resource systemPromptTemplate;

    @GetMapping("/chat")
    public String chat(String message) {
        return chatClient
                .prompt()
                .system(systemPromptTemplate)
                .user(message)
                .call()
                .content();
    }

}
