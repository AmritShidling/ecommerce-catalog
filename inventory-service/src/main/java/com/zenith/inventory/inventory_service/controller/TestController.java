package com.zenith.inventory.inventory_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/test-trace")
    public String test() {
        return "Tracing test";
    }
}
