package com.zenith.catalog.catalog_service.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductRequestDTO (
    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 100)
    String name,
    @Size(max = 1000)
    String description,
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false)
    BigDecimal price,
    @NotBlank(message = "Category is required")
    String category
) {}
