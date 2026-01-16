package com.zenith.recommendation.recommendation_service.client;

import com.zenith.recommendation.recommendation_service.ProductResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "catalog-service")
public interface CatalogClient {

    @GetMapping("/ai/search")
    List<ProductResponseDTO> searchSimilar(@RequestParam("query") String query);
}
