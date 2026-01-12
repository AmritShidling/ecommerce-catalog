package com.zenith.inventory.inventory_service.services;

import com.zenith.inventory.inventory_service.DTO.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderEventListener {

    private final InventoryService inventoryService;

    @KafkaListener(topics = "order-placed-events", groupId = "inventory-group")
    public void handleOrderPlacedEvent(OrderPlacedEvent event){
        log.info("Received Order Event for Order Number: {}", event.orderNumber());
        inventoryService.handleOrderPlacedEvent(event);
//
//        try {
//            inventoryService.reduceStock(event.orderLineItems());
//            log.info("Stock reduced successfully for order: {}", event.orderNumber());
//        } catch (Exception e){
//            log.error("Failed to reduce stocker for order {}: {}", event.orderNumber());
//        }

    }
}
