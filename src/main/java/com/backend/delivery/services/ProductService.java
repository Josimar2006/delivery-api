package com.backend.delivery.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.delivery.DTO.CreateProductDto;
import com.backend.delivery.enums.ProductCategory;
import com.backend.delivery.models.Product;
import com.backend.delivery.models.Store;
import com.backend.delivery.repositories.ProductRepository;
import com.backend.delivery.repositories.StoreRepository;

@Service
public class ProductService {
    
    @Autowired
    StoreRepository storeRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    FileService fileService;

    public Product save(CreateProductDto data) {
        Store store = storeRepository.findById(data.store_id())
            .orElseThrow(() -> new RuntimeException("Store Not found"));

        String filename = fileService.upload(data.product_image());
        Product product = Product.builder()
            .product_name(data.product_name())
            .price(data.price())
            .quantity(data.quantity())
            .category(data.category())
            .description(data.description())
            .product_image(filename)
            .store(store)
            .build();

        return productRepository.save(product);
    }

    public Product update(long id, CreateProductDto data) {
        
        Store store = storeRepository.findById(data.store_id())
            .orElseThrow(() -> new RuntimeException("Store Not found"));

        Product product = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product Not found"));

        String filename = fileService.upload(data.product_image());

        product.setCategory(data.category());
        product.setDescription(data.description());
        product.setPrice(data.price());
        product.setStore(store);
        product.setQuantity(data.quantity());
        product.setProduct_name(data.product_name());
        product.setProduct_image(filename);
        
        return productRepository.save(product);
    }

    public Product findOne(long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("upps!, Product not found"));
    }

    public void delete(long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("upps!, Product not found"));

        productRepository.delete(product);
    }

    public Page<Product> all(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return productRepository.findAll(pageable);
    }

    @Transactional
    public Page<Product> findByCategory(int pageNumber, int pageSize, ProductCategory category) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return productRepository.findAllByCategory(category, pageable);
    }
}
