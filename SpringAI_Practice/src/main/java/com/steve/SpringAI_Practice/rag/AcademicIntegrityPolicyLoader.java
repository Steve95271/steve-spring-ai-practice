package com.steve.SpringAI_Practice.rag;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AcademicIntegrityPolicyLoader {

    private final VectorStore vectorStore;

    @Value("classpath:Academic-Integrity-Policy.pdf")
    Resource academicIntegrityPolicyFile;

    public AcademicIntegrityPolicyLoader(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    public void loadPDF() {
        TikaDocumentReader docReader = new TikaDocumentReader(academicIntegrityPolicyFile);
        List<Document> docs = docReader.get();
        TextSplitter textSplitter = TokenTextSplitter
                .builder()
                .withChunkSize(150)
                .withMaxNumChunks(350)
                .build();
        vectorStore.add(textSplitter.split(docs));
    }

}
