package com.zenith.catalog.catalog_service.service.impl;

import com.zenith.catalog.catalog_service.document.ProductDocument;
import com.zenith.catalog.catalog_service.dto.ProductRequestDTO;
import com.zenith.catalog.catalog_service.dto.ProductResponseDTO;
import com.zenith.catalog.catalog_service.entity.ProductEntity;
import com.zenith.catalog.catalog_service.mapper.ProductMapper;
import com.zenith.catalog.catalog_service.repository.elastic.ProductElasticRepository;
import com.zenith.catalog.catalog_service.repository.jpa.ProductJpaRepository;
import com.zenith.catalog.catalog_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductJpaRepository productJpaRepository;
    private final ProductElasticRepository productElasticRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponseDTO saveProduct(ProductRequestDTO productRequestDTO) {
        log.info("Saving product to Postgres: {}", productRequestDTO.name());
        ProductEntity entity = productMapper.toEntity(productRequestDTO);
        entity.setCreatedAt(LocalDateTime.now());
        ProductEntity savedProduct = productJpaRepository.save(entity);
        ProductDocument document = productMapper.toDocument(savedProduct);
        try {
            productElasticRepository.save(document);
            log.info("Product synced to Elasticsearch successfully");
        }
        catch (Exception e){
            log.error("Failed to sync to Elasticsearch: {}", e.getMessage());
        }
        return productMapper.toResponseDTO(savedProduct);
    }

    @Override
    public List<ProductResponseDTO> searchProducts(String query) {
        log.info("Performing fuzzy search in ES for: {}", query);
        return productElasticRepository.findByNameContaining(query)
                .stream()
                .map(productMapper::toResponseDTO)
                .toList();
    }

    @Override
    public ProductResponseDTO getProduct(Long id) {
        log.info("Performing GET request for product with ID: {}", id);
        Optional<ProductEntity> productEntity = productJpaRepository.findById(id);
        return productEntity.map(productMapper::toResponseDTO).orElseThrow(null);
    }

    @Override
    public void deleteProduct(Long id) {
        log.info("Deleting the product with id: {}", id);
        productJpaRepository.deleteById(id);
    }
}
