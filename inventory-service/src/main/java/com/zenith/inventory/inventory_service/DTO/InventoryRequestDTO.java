package com.zenith.inventory.inventory_service.DTO;

public record InventoryRequestDTO(
        String productCode,
        Integer totalQuantity,
        Integer quantityReserved
) {
}
