package com.zenith.inventory.inventory_service.DTO;

public record InventoryResponseDTO(
        Integer id,
        String productCode,
        String totalQuantity,
        String quantityReserved
) {
}
