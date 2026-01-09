package com.zenith.inventory.inventory_service.services;

import com.zenith.inventory.inventory_service.DTO.InventoryResponse;
import com.zenith.inventory.inventory_service.DTO.InventoryResponseDTO;
import com.zenith.inventory.inventory_service.DTO.OrderLineItemDto;
import com.zenith.inventory.inventory_service.entity.Inventory;
import com.zenith.inventory.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public List<InventoryResponse>  checkStock(List<String> skuCodes) {
        List<Inventory> list = inventoryRepository.findBySkuCodeIn(skuCodes);
        log.info("Values");
        for(Inventory i: list) log.info(i.getSkuCode()  +" "+ i.getTotalQuantity());
        return inventoryRepository.findBySkuCodeIn(skuCodes).stream()
                .map(inventory ->
                        new InventoryResponse(inventory.getSkuCode(), inventory.getTotalQuantity())).toList();
    }

    public void reduceStock(List<OrderLineItemDto> orders){
        for(OrderLineItemDto order: orders){
            Inventory inventory = inventoryRepository.getBySkuCode(order.skuCode()).orElseThrow(()->new RuntimeException("Item not found"));
            if(inventory.getTotalQuantity() < order.quantity()){
                throw new RuntimeException("Not sufficient quantities");
            }
            inventory.setTotalQuantity(inventory.getTotalQuantity() - order.quantity());
            inventoryRepository.save(inventory);
        }
    }

    public List<InventoryResponseDTO> getAllInventory() {
        List<Inventory> inventories =inventoryRepository.findAll();
        return inventories.stream().map( inventory -> new InventoryResponseDTO( inventory.getId(), inventory.getSkuCode(), inventory.getTotalQuantity()+"", "0")).toList();
    }


    public void deleteAll() {
        inventoryRepository.deleteAll();
    }
}
