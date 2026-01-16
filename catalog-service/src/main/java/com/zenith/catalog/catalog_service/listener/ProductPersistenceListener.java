package com.zenith.catalog.catalog_service.listener;

import com.zenith.catalog.catalog_service.entity.ProductEntity;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;
import java.util.Map;

@Component
public class ProductPersistenceListener {
    private final VectorStore vectorStore;

    public ProductPersistenceListener(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Async // Don't make the Admin wait for the AI to finish
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onProductSaved(ProductEntity product) {
        // We use the data from your DTO fields to create the AI 'Document'
        Document document = new Document(
                String.format("Name: %s. Description: %s. Category: %s.",
                        product.getName(), product.getDescription(), product.getCategory()),
                Map.of("id", product.getId())
        );

        vectorStore.add(List.of(document));
        System.out.println("AI Vector created for Product: " + product.getId());
    }
}
