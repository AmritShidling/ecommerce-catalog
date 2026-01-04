package com.zenith.catalog.catalog_service.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("Product with ID " + id + " was not found in our catalog.");
    }
}
