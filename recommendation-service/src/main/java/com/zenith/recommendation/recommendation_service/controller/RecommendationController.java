package com.zenith.recommendation.recommendation_service.controller;

import com.zenith.recommendation.recommendation_service.DTO.ProductResponseDTO;
import com.zenith.recommendation.recommendation_service.client.CatalogClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
@Slf4j
public class RecommendationController {
    private final CatalogClient catalogClient;
    public RecommendationController(CatalogClient catalogClient){
        this.catalogClient = catalogClient;
    }
    @GetMapping("/search")
    public List<ProductResponseDTO> getRecommendations(@RequestParam String query){
        log.info("Trying to fetch the recommendations for query: {}", query);
        return catalogClient.searchSimilar(query);
    }
}
