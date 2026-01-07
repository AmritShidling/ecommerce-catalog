package com.zenith.inventory.inventory_service.services.kafka;

import com.zenith.inventory.inventory_service.DTO.ProductCreatedEvent;
import com.zenith.inventory.inventory_service.entity.Inventory;
import com.zenith.inventory.inventory_service.mapper.InventoryMapper;
import com.zenith.inventory.inventory_service.repository.InventoryRepository;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryConsumer {
    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;
    private final ObservationRegistry observationRegistry;
    @KafkaListener(topics = "product-created-events", groupId = "inventory-group")
    public void consumeProductCreated(ProductCreatedEvent event){
        Observation observation = observationRegistry.getCurrentObservation();
        if(observation != null){
            observation.highCardinalityKeyValue("product.id", String.valueOf(event.id()));
            observation.highCardinalityKeyValue("product.name", event.name());
        }

        log.info("Received event from Kafka: {}", event);
        log.info("Received event for product: {}. Adjusting stock...", event.name());
        Inventory inventory = inventoryMapper.fromEventToEntity(event);
        inventoryRepository.save(inventory);
        log.info("Initialized inventory for product ID: {}", event.id());

    }
}
