package com.zenith.inventory.inventory_service.DTO;

public record StockUpdateEvent(
        String productId,
        Integer quantityChange,
        String operationTye
) {
}
