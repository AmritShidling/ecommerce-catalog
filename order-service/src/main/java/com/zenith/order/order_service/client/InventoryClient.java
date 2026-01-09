package com.zenith.order.order_service.client;

import com.zenith.order.order_service.DTO.InventoryResponse;
import com.zenith.order.order_service.DTO.OrderLineItemDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "inventory-service")
public interface InventoryClient {
    @GetMapping("/api/inventory")
    List<InventoryResponse> checkStock(@RequestParam("skuCode") List<String> skuCodes);


    @PostMapping("/api/inventory/reduce")
    void reduceStock(@RequestBody List<OrderLineItemDto> orders);
}
