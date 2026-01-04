package com.zenith.catalog.catalog_service.dto;

import java.time.LocalDateTime;

public record ErroeResponse(
        String message,
        LocalDateTime timeStamp,
        int status,
        String error,
        String path
) {
}
