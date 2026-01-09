package com.zenith.order.order_service.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "inventory-service")
public class InventoryClient {
    
    boolean checkStock(@RequestParam("skuCode") List<String> skuCodes);
}
