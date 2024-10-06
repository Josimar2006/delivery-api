package com.backend.delivery.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.delivery.enums.ProductCategory;
import com.backend.delivery.models.Product;
import com.backend.delivery.models.Store;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByCategory(ProductCategory category, Pageable pageable); 
    Page<Product> findAllByStore(Store store, Pageable pageable);   
}
