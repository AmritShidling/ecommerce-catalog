package com.zenith.inventory.inventory_service.services;

import com.zenith.inventory.inventory_service.DTO.*;
import com.zenith.inventory.inventory_service.entity.Inventory;
import com.zenith.inventory.inventory_service.repository.InventoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    @Autowired
    @Lazy
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public List<InventoryResponse>  checkStock(List<String> skuCodes) {
        List<Inventory> list = inventoryRepository.findBySkuCodeIn(skuCodes);
        log.info("Values");
        for(Inventory i: list) log.info(i.getSkuCode()  +" "+ i.getTotalQuantity());
        return inventoryRepository.findBySkuCodeIn(skuCodes).stream()
                .map(inventory ->
                        new InventoryResponse(inventory.getSkuCode(), inventory.getTotalQuantity())).toList();
    }


    public void handleOrderPlacedEvent(OrderPlacedEvent event){
        boolean allInStock = event.orderLineItems().stream()
                .allMatch(item -> inventoryRepository.findBySkuCode(item.skuCode())
                        .map(inv -> inv.getTotalQuantity() >= item.quantity())
                        .orElse(false));

        if(allInStock){
            event.orderLineItems().forEach(
                    item -> {
                        Inventory inventory = inventoryRepository.findBySkuCode(item.skuCode()).orElseThrow();
                        inventory.setTotalQuantity(inventory.getTotalQuantity() - item.quantity());
                        inventoryRepository.save(inventory);
                    }
            );
            kafkaTemplate.send("inventory-check-results", new InventoryResultEvent(event.orderNumber(), "SUCCESS"))
                    .whenComplete((result, ex) -> {
                if (ex == null) {
                    log.info("SENT SUCCESSFULLY to Kafka: {}", result.getRecordMetadata().offset());
                } else {
                    log.error("KAFKA SEND FAILED!", ex);
                }
            });
            log.info("Stock reserved for the order: {}", event.orderNumber());
        }
        else{
            kafkaTemplate.send("inventory-check-result", new InventoryResultEvent(event.orderNumber(), "FAILURE"));
            log.info("Stock check failed for order: {}", event.orderNumber());
        }
    }

    @Transactional
    public void reduceStock(List<OrderLineItemDto> orders){
        for(OrderLineItemDto order: orders){
            int rowUpdated = inventoryRepository.decreaseStock(order.skuCode(), order.quantity());
            if(rowUpdated == 0){
                throw new RuntimeException("Race condition: Insufficient stock for " + order.skuCode());
            }
        }
    }

    public List<InventoryResponseDTO> getAllInventory() {
        List<Inventory> inventories =inventoryRepository.findAll();
        return inventories.stream().map( inventory -> new InventoryResponseDTO( inventory.getId(), inventory.getSkuCode(), inventory.getTotalQuantity()+"", "0")).toList();
    }


    public void deleteAll() {
        inventoryRepository.deleteAll();
    }
}
