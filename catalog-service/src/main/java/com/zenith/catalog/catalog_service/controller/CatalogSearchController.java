package com.zenith.catalog.catalog_service.controller;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/catalog")
public class CatalogSearchController {
    private final VectorStore vectorStore;
    public CatalogSearchController(VectorStore vectorStore){
        this.vectorStore = vectorStore;
    }

    @GetMapping("/search")
    public List<Document> semanticSearch(@RequestParam String query){
        return vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(query)
                        .topK(5) // Return top 5 matches
                        .similarityThreshold(0.7)
                        .build()
        );
    }
}
