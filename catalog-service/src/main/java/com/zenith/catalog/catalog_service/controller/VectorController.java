package com.zenith.catalog.catalog_service.controller;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ai")
public class VectorController {
    private final VectorStore vectorStore;
    public VectorController(VectorStore vectorStore){
        this.vectorStore = vectorStore;
    }
    @GetMapping("/load")
    public String load() {
        List<Document> documents = List.of(
                new Document("The MacBook Pro has a liquid retina display.", Map.of("category", "laptop")),
                new Document("The iPhone 15 uses a USB-C charging port.", Map.of("category", "phone")),
                new Document("The Apple Watch is great for fitness tracking.", Map.of("category", "wearable"))
        );
        vectorStore.add(documents);
        return "Data loaded into PGVector successfully!";
    }

    @GetMapping("/search")
    public List<Map<String, Object>> search(@RequestParam String query) {
        // topK(5) finds the 5 most similar items
        List<Document> results = vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(query)
                        .topK(5)
                        .build()
        );

        // Convert Documents to a simple Map list for the Feign Client to consume
        return results.stream()
                .map(doc -> {
                    Map<String, Object> map = new HashMap<>(doc.getMetadata());
                    map.put("description", doc.getContent());
                    return map;
                })
                .toList();
    }
}
