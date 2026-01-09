package com.zenith.inventory.inventory_service.DTO;

public record InventoryResponseDTO(
        Long id,
        String skuCode,
        String totalQuantity,
        String quantityReserved
) {
}
