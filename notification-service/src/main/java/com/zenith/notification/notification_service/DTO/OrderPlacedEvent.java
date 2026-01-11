package com.zenith.notification.notification_service.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record OrderPlacedEvent(
        @JsonProperty("orderNumber") String orderNumber,
        @JsonProperty("userId") String userId,
        @JsonProperty("orderLineItem") List<OrderLineItemDto> orderLineItems) {
}
