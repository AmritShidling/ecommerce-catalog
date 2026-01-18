package com.zenith.catalog.catalog_service.listener;

import com.zenith.catalog.catalog_service.dto.ProductRequestDTO;
import com.zenith.catalog.catalog_service.dto.ProductResponseDTO;
import com.zenith.catalog.catalog_service.entity.ProductEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;


import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class ProductAIListener {
    private final VectorStore vectorStore;
    public ProductAIListener(VectorStore vectorStore){
        this.vectorStore = vectorStore;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleProductSave(ProductEntity product){
        log.info(">>> Starting AI Vectorization for: {}", product.getName());
        Document document = new Document(
                "Product: " + product.getName() + ". " + product.getDescription(),
                Map.of("productId", product.getId(), "category", product.getCategory())
        );
        vectorStore.add(List.of(document));
    }
}
