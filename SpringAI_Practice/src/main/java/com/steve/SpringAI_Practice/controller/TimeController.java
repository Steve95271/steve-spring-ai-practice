package com.steve.SpringAI_Practice.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@RestController
@RequestMapping("/api/tools")
public class TimeController {

    private final ChatClient timeChatClient;

    public TimeController(@Qualifier("timeChatClient") ChatClient timeChatClient) {
        this.timeChatClient = timeChatClient;
    }

    @GetMapping("local-time")
    public ResponseEntity<String> localTime(
            @RequestHeader("username") String username,
            @RequestParam("message") String message
    ) {
        return ResponseEntity.ok(
                timeChatClient
                        .prompt()
                        .advisors(a -> a.param(CONVERSATION_ID, username))
                        .user(message)
                        .call()
                        .content()
        );
    }

}
