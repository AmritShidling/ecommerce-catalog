package com.zenith.catalog.catalog_service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductCreatedEvent(
        Long id,
        String name,
        BigDecimal price,
        Integer initialStock
) {
}
