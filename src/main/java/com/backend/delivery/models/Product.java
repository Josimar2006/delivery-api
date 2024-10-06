package com.backend.delivery.models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.backend.delivery.enums.ProductCategory;

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
@Table(name = "products")
public class Product {
 
    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String product_name;

    @Column(precision = 2)
    private double price;

    private byte quantity;

    private String product_image;

    private ProductCategory category;

    @Lob
    @Column(nullable = true)
    private String description;

    @ManyToOne()
    @JoinColumn(name = "store_id", referencedColumnName = "id")
    private Store store;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
}
