package com.steve.SpringAI_Practice.config;

import com.steve.SpringAI_Practice.advisors.TokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient ollamaChatClient(OllamaChatModel ollamaChatModel) {
        ChatClient.Builder chatClientBuilder = ChatClient.builder(ollamaChatModel);
        return chatClientBuilder
                .defaultAdvisors(List.of(new SimpleLoggerAdvisor(), new TokenUsageAuditAdvisor()))
                .build();
    }

    @Bean
    public ChatClient openaiChatClient(OpenAiChatModel openAiChatModel) {
        OpenAiChatOptions openAiChatOptions = OpenAiChatOptions.builder().model("gpt-4o-mini").temperature(0.8).build();
        ChatClient.Builder chatClientBuilder = ChatClient.builder(openAiChatModel);
        return chatClientBuilder
                .defaultOptions(openAiChatOptions)
                .defaultAdvisors(List.of(new SimpleLoggerAdvisor(), new TokenUsageAuditAdvisor()))
                .build();
    }

}
