package com.zenith.inventory.inventory_service.mapper;

import com.zenith.inventory.inventory_service.DTO.InventoryRequestDTO;
import com.zenith.inventory.inventory_service.DTO.InventoryResponseDTO;
import com.zenith.inventory.inventory_service.DTO.ProductCreatedEvent;
import com.zenith.inventory.inventory_service.entity.Inventory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
    Inventory toEntity(InventoryResponseDTO responseDTO);
    InventoryResponseDTO toResponseDTO(Inventory inventory);
    Inventory toEntity(InventoryRequestDTO requestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "skuCode", source = "id")
    @Mapping(target = "reservedQuantity", constant = "0")
    @Mapping(target = "totalQuantity", source = "initialStock")
    Inventory fromEventToEntity(ProductCreatedEvent event);
}
