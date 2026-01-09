package com.zenith.inventory.inventory_service.DTO;

import java.math.BigDecimal;

public record OrderLineItemDto(Long productId, String skuCode, BigDecimal price, Integer quantity) {
}
