package com.zenith.catalog.catalog_service.service.kafka;

import com.zenith.catalog.catalog_service.dto.ProductCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductProducer {
    private final KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;
    private static final String TOPIC = "product-created-events";
    public void sendProductCreatedEvent(ProductCreatedEvent productCreatedEvent){
        log.info("Publishing event to Kafka {}", productCreatedEvent);
        kafkaTemplate.send(TOPIC, productCreatedEvent.id().toString(), productCreatedEvent);
    }

}
