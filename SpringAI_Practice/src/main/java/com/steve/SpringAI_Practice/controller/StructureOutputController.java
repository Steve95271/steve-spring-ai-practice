package com.steve.SpringAI_Practice.controller;

import com.steve.SpringAI_Practice.pojo.CountryCities;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class StructureOutputController {
    private final ChatClient chatClient;

    public StructureOutputController(@Qualifier("openaiChatClient") ChatClient chatClientBuilder) {
        this.chatClient = chatClientBuilder;
    }

    @GetMapping("/chat-bean")
    public CountryCities chatBean(String message) {
        return chatClient
                .prompt()
                .user(message)
                .call()
                .entity(CountryCities.class);
    }

}
