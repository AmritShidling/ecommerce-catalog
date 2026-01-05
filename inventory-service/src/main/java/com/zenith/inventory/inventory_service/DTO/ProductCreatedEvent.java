package com.zenith.inventory.inventory_service.DTO;

import java.math.BigDecimal;

public record ProductCreatedEvent(
        Long id,
        String name,
        BigDecimal price,
        Integer initialStock
) {
}
