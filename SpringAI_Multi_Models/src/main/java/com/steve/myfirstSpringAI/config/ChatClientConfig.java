package com.steve.myfirstSpringAI.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient openAiChatClient(OpenAiChatModel openAiChatModel) {
        return ChatClient.create(openAiChatModel);
    }

    @Bean
    public ChatClient ollamaChatClient(OllamaChatModel OllamaChatModel) {
        /*
        * This second approach creates a Chat Client which allow developer customize the configuration.
        * */

        ChatClient.Builder chatClientBuilder = ChatClient.builder(OllamaChatModel);
        return chatClientBuilder.build();
    }

}
