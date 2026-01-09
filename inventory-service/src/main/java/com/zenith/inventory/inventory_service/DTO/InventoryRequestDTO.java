package com.zenith.inventory.inventory_service.DTO;

public record InventoryRequestDTO(
        String skuCode,
        Integer totalQuantity,
        Integer quantityReserved
) {
}
