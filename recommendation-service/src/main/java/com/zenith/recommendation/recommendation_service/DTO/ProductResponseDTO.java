package com.zenith.recommendation.recommendation_service.DTO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponseDTO(
        Long id,
        String name,
        String description,
        String category,
        BigDecimal price,
        LocalDateTime createdAt
) implements Serializable {
}
