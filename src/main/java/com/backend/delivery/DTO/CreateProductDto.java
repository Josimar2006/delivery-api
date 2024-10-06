package com.backend.delivery.DTO;

import org.springframework.web.multipart.MultipartFile;

import com.backend.delivery.enums.ProductCategory;

import jakarta.validation.constraints.NotNull;

public record CreateProductDto(
    @NotNull
    String product_name,

    @NotNull
    double price,


    @NotNull
    byte quantity,

    @NotNull
    long store_id,

    String description, 
    
    @NotNull
    ProductCategory category,

    @NotNull
    MultipartFile product_image
) {
    
}
