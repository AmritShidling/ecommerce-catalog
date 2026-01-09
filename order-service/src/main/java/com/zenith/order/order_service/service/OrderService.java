package com.zenith.order.order_service.service;

import com.zenith.order.order_service.DTO.InventoryResponse;
import com.zenith.order.order_service.DTO.OrderLineItemDto;
import com.zenith.order.order_service.DTO.OrderRequest;
import com.zenith.order.order_service.client.InventoryClient;
import com.zenith.order.order_service.entity.OrderEntity;
import com.zenith.order.order_service.entity.OrderItem;
import com.zenith.order.order_service.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
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
        order.setOrderNumber(java.util.UUID.randomUUID().toString());
        List<String> skuCodes = orderRequest.orderLineItems().stream().map(OrderLineItemDto::skuCode).toList();
        List<InventoryResponse>  inventoryResponseList = inventoryClient.checkStock(skuCodes);

        validateStock(orderRequest, inventoryResponseList);

        List<OrderItem> orderItems = orderRequest.orderLineItems().stream().map(this::mapToDto).toList();
        order.setOrderItems(orderItems);
        orderRepository.save(order);

        inventoryClient.reduceStock(orderRequest.orderLineItems());
        return "Order Placed Successfully";

    }


}
