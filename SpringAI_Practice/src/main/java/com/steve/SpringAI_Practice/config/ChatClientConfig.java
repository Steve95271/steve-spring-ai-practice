package com.steve.SpringAI_Practice.config;

import com.steve.SpringAI_Practice.advisors.TokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
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

    @Bean
    public ChatClient chatMemoryOpenAiChatClient(OpenAiChatModel openAiChatModel, ChatMemory chatMemory) {
        Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
        Advisor loggerAdvisor = new SimpleLoggerAdvisor();
        OpenAiChatOptions openAiChatOptions = OpenAiChatOptions.builder().model("gpt-4o-mini").temperature(0.7).build();
        ChatClient.Builder chatClientBuilder = ChatClient.builder(openAiChatModel);

        return chatClientBuilder
                .defaultOptions(openAiChatOptions)
                .defaultAdvisors(List.of(loggerAdvisor, memoryAdvisor))
                .build();
     }

    @Bean
    public ChatClient chatMemoryOllamaChatClient(
            OllamaChatModel ollamaChat,
            ChatMemory chatMemory,
            RetrievalAugmentationAdvisor retrievalAugmentationAdvisor
    ) {
        Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
        Advisor loggerAdvisor = new SimpleLoggerAdvisor();
        OllamaOptions ollamaOptions = OllamaOptions.builder().temperature(0.7).build();

        return ChatClient
                .builder(ollamaChat)
                .defaultOptions(ollamaOptions)
                .defaultAdvisors(
                        List.of(
                                loggerAdvisor,
                                memoryAdvisor,
                                retrievalAugmentationAdvisor
                        )
                )
                .build();
    }

}
