package com.steve.SpringAI_System_Role.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PromptTemplateController {

    private final ChatClient chatClient;

    public PromptTemplateController(@Qualifier("ollamaChatClient") ChatClient ollamaChatClient) {
        this.chatClient = ollamaChatClient;
    }

    @Value("classpath:/promptTemplates/userPromptTemplate.st")
    Resource promptTemplate;

    @GetMapping("/email")
    public String emailResponse(
            @RequestParam("customerName") String customerName,
            @RequestParam("customerMessage") String customerMessage
    ) {
        return chatClient
                .prompt()
                .system("""
                        Your are a professional customer service assistant which helps drafting email\s
                        responses to improve the productivity of the customer support team
                       """)
                .user(promptTemplateSpec -> promptTemplateSpec
                        .text(promptTemplate)
                        .param("customerName", customerName)
                        .param("customerMessage", customerMessage)
                )
                .call()
                .content();
    }

}
