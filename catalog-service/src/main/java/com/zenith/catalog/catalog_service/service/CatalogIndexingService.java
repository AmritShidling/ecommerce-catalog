package com.zenith.catalog.catalog_service.service;

import com.zenith.catalog.catalog_service.repository.jpa.ProductJpaRepository;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CatalogIndexingService {
    private VectorStore vectorStore;
    private ProductJpaRepository productJpaRepository;

    public CatalogIndexingService(VectorStore vectorStore, ProductJpaRepository productJpaRepository){
        this.vectorStore = vectorStore;
        this.productJpaRepository = productJpaRepository;
    }

    public void indexProduct(){
        List<Document> documents = productJpaRepository.findAll().stream()
                .map(product -> new Document(
                        product.getName() +":"+product.getDescription(),
                        Map.of("productId", product.getId(), "catagory", product.getCategory())
                ))
                .toList();
        vectorStore.add(documents);
    }
}
