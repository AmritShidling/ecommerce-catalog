package com.zenith.catalog.catalog_service.config;

import com.zenith.catalog.catalog_service.repository.jpa.ProductJpaRepository;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class VectorDataInitializer implements CommandLineRunner {
    private final VectorStore vectorStore;
    private final ProductJpaRepository productRepository;

    public VectorDataInitializer(VectorStore vectorStore, ProductJpaRepository productRepository) {
        this.vectorStore = vectorStore;
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) {
        // 1. Fetch products from your standard SQL table
//        var products = productRepository.findAll();
//
//        // 2. Convert them into AI Documents
//        List<Document> documents = products.stream()
//                .map(p -> new Document(
//                        p.getName() + ": " + p.getDescription(), // The text the AI "reads"
//                        Map.of("productId", p.getId(), "category", p.getCategory()) // Metadata for filtering
//                ))
//                .toList();
//
//        // 3. This line triggers the OpenAI API call and Saves to Postgres
//        vectorStore.add(documents);
//
//        System.out.println("✅ Vector Store populated with " + documents.size() + " products.");
    }
}
