package com.zenith.catalog.catalog_service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponseDTO (
        Long id,
        String name,
        String description,
        String category,
        BigDecimal price,
        LocalDateTime createdAt
){
}
