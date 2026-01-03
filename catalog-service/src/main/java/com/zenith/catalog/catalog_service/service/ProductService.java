package com.zenith.catalog.catalog_service.service;

import com.zenith.catalog.catalog_service.dto.ProductRequestDTO;
import com.zenith.catalog.catalog_service.dto.ProductResponseDTO;

import java.util.List;

public interface ProductService {
    ProductResponseDTO saveProduct(ProductRequestDTO productRequestDTO);
    List<ProductResponseDTO> searchProducts(String query);
    ProductResponseDTO getProduct(Long id);
    void deleteProduct(Long id);
}
