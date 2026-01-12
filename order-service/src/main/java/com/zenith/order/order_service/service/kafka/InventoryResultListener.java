package com.zenith.order.order_service.service.kafka;

import com.zenith.order.order_service.DTO.InventoryResultEvent;
import com.zenith.order.order_service.entity.OrderEntity;
import com.zenith.order.order_service.repository.OrderRepository;
import com.zenith.order.order_service.util.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryResultListener {
    private final OrderRepository orderRepository;

    @KafkaListener(topics = "inventory-check-results", groupId = "inventory-result-group", containerFactory = "kafkaListenerContainerFactory")
    public void handleInventoryResult(InventoryResultEvent event){
        log.info("Received inventory result for order: {}", event.orderNumber());
        OrderEntity order = orderRepository.findByOrderNumber(event.orderNumber()).orElseThrow(()-> new RuntimeException("Order not exist"));
        if("SUCCESS".equals(event.result())){
            order.setStatus(Status.CONFIRMED);
            log.info("Order {} is PLACED", event.orderNumber());
        }
        else {
            order.setStatus(Status.REJECTED);
            log.info("Order: {} is not placed due to stock issues", event.orderNumber());
        }
        orderRepository.save(order);

    }
}
