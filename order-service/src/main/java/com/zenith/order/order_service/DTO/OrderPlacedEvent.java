package com.zenith.order.order_service.DTO;

import com.zenith.order.order_service.util.Status;

import java.util.List;

public record OrderPlacedEvent(String orderNumber, String userId, List<OrderLineItemDto> orderLineItems) {
}
