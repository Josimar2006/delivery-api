package com.backend.delivery.models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.backend.delivery.enums.StoreCategory;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "stores")
public class Store {
    
    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String store_name;

    private double rating;

    private String store_image;

    @Enumerated(EnumType.STRING)
    private StoreCategory category;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
