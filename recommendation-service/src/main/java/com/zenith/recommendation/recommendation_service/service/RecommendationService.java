package com.zenith.recommendation.recommendation_service.service;

import com.zenith.recommendation.recommendation_service.ProductResponseDTO;
import com.zenith.recommendation.recommendation_service.client.CatalogClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationService {
    private final CatalogClient catalogClient;
    public RecommendationService(CatalogClient catalogClient){
        this.catalogClient = catalogClient;

    }

    public List<ProductResponseDTO> getRecommendationsForUser(String interest){
        String refinedQuery = "Best product related to: "+ interest;
        return catalogClient.searchSimilar(refinedQuery);
    }
}
