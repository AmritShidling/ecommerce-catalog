package com.zenith.order.order_service.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zenith.order.order_service.entity.OrderItem;
import com.zenith.order.order_service.util.Status;

import java.util.List;

public record OrderPlacedEvent(
        @JsonProperty("orderNumber") String orderNumber,
        @JsonProperty("userId") String userId,
        @JsonProperty("orderLineItem") List<OrderItem> orderLineItems) {
}
