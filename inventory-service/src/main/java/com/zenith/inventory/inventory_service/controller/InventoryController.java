package com.zenith.inventory.inventory_service.controller;

import com.zenith.inventory.inventory_service.DTO.InventoryResponse;
import com.zenith.inventory.inventory_service.DTO.InventoryResponseDTO;
import com.zenith.inventory.inventory_service.DTO.OrderLineItemDto;
import com.zenith.inventory.inventory_service.entity.Inventory;
import com.zenith.inventory.inventory_service.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> checkStock(@RequestParam List<String> skuCode){
        return inventoryService.checkStock(skuCode);
    }
    @PostMapping("/reduce")
    @ResponseStatus(HttpStatus.OK)
    public void reduceStock(@RequestBody List<OrderLineItemDto> items){
        inventoryService.reduceStock(items);
    }
    @GetMapping("/all")
    public List<InventoryResponseDTO> getAllInventories(){
        return inventoryService.getAllInventory();
    }

    @DeleteMapping("/delete")
    public void deleteAll(){
        inventoryService.deleteAll();
    }
}
