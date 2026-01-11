package com.zenith.order.order_service.service;

import com.zenith.order.order_service.DTO.InventoryResponse;
import com.zenith.order.order_service.DTO.OrderLineItemDto;
import com.zenith.order.order_service.DTO.OrderPlacedEvent;
import com.zenith.order.order_service.DTO.OrderRequest;
import com.zenith.order.order_service.client.InventoryClient;
import com.zenith.order.order_service.entity.OrderEntity;
import com.zenith.order.order_service.entity.OrderItem;
import com.zenith.order.order_service.repository.OrderRepository;
import com.zenith.order.order_service.util.Status;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;
//    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;
    public OrderEntity findByOrderNumber(String orderNumber) {
        Optional<OrderEntity> order = orderRepository.findByOrderNumber(orderNumber);

        return order.orElseThrow(RuntimeException::new);
    }

    public OrderItem mapToDto(OrderLineItemDto orderLineItemDto){
        OrderItem orderItem = new OrderItem();
        orderItem.setPrice(orderLineItemDto.price());
        orderItem.setQuantity(orderLineItemDto.quantity()); // Get from DTO
        orderItem.setSkuCode(orderLineItemDto.skuCode());   // Get from DTO
        return orderItem;
    }

    private void validateStock(OrderRequest request, List<InventoryResponse> inventoryResponses){
        Map<String, Integer> inventoryMap = inventoryResponses.stream()
                .collect(Collectors.toMap(InventoryResponse::skuCode, InventoryResponse::availableQuantity));
        for(OrderLineItemDto orderLineItemDto: request.orderLineItems()){
            if(inventoryMap.get(orderLineItemDto.skuCode()) == null){
                throw new RuntimeException("Item not available");
            }
            else if(inventoryMap.get(orderLineItemDto.skuCode()) < orderLineItemDto.quantity()){
                throw new RuntimeException("Not enough stocks for "+ orderLineItemDto.skuCode() +"! Required: " + orderLineItemDto.quantity() + " Available: " + inventoryMap.get(orderLineItemDto.skuCode()) );
            }
        }
    }

    @Transactional
    public String placeOrder(OrderRequest orderRequest) {
        OrderEntity order = new OrderEntity();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setStatus(Status.PENDING);
        order.setUserId(orderRequest.userId());
        log.info("USer ID ------- {} ", orderRequest.userId());

        List<OrderItem> orderItems  = orderRequest.orderLineItems().stream().map(this::mapToDto).toList();
        order.setOrderItems(orderItems);
        orderRepository.save(order);

        OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent(order.getOrderNumber(), String.valueOf(order.getUserId()), orderItems);
        kafkaTemplate.send("order-placed-events", orderPlacedEvent);
        return "Order submitted ( Order number: " + order.getOrderNumber() +")";
    }


}
