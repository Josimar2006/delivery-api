package com.backend.delivery.DTO;

import org.springframework.web.multipart.MultipartFile;

import com.backend.delivery.enums.StoreCategory;

import jakarta.validation.constraints.NotNull;

public record CreateStoreDto(
    @NotNull
    String store_name,

    @NotNull
    double rating,

    @NotNull
    StoreCategory category,

    @NotNull
    MultipartFile store_image
) {
    
}
