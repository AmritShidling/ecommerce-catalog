package com.zenith.catalog.catalog_service.mapper;

import com.zenith.catalog.catalog_service.document.ProductDocument;
import com.zenith.catalog.catalog_service.dto.ProductRequestDTO;
import com.zenith.catalog.catalog_service.dto.ProductResponseDTO;
import com.zenith.catalog.catalog_service.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductEntity toEntity(ProductRequestDTO productRequestDTO);
    ProductDocument toDocument(ProductEntity entity);
    ProductResponseDTO toResponseDTO(ProductEntity entity);
    ProductResponseDTO toResponseDTO(ProductDocument document);
}
