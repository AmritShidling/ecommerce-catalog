package com.zenith.inventory.inventory_service.services.kafka;

import com.zenith.inventory.inventory_service.DTO.ProductCreatedEvent;
import com.zenith.inventory.inventory_service.entity.Inventory;
import com.zenith.inventory.inventory_service.mapper.InventoryMapper;
import com.zenith.inventory.inventory_service.repository.InventoryRepository;
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

    @KafkaListener(topics = "product-created-events", groupId = "inventory-group")
    public void consumeProductCreated(ProductCreatedEvent event){
        log.info("Received event from Kafka: {}", event);

        Inventory inventory = inventoryMapper.fromEventToEntity(event);
        inventoryRepository.save(inventory);
        log.info("Initialized inventory for product ID: {}", event.id());

    }
}
