package com.zenith.inventory.inventory_service.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public record InventoryResultEvent(
        @JsonProperty("orderNumber") String orderNumber,
        @JsonProperty("result") String result) {
}
