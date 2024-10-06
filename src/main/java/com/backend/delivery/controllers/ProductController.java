package com.backend.delivery.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.delivery.DTO.CreateProductDto;
import com.backend.delivery.enums.ProductCategory;
import com.backend.delivery.models.Product;
import com.backend.delivery.services.ProductService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/v1/product")
@Slf4j
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping
    public ResponseEntity<Page<Product>> all(
        @RequestParam(defaultValue = "0", name = "skip") int pageNumber,
        @RequestParam(defaultValue = "10", name = "size") int pageSize
    ) {
        log.info("listar produtos");
        
        return ResponseEntity.ok().body(productService.all(pageNumber, pageSize));
    }

    @PostMapping
    public ResponseEntity<Product> create(@ModelAttribute @Valid CreateProductDto data) {
        log.info("criar um novo produto");

        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(data));
    }

    @PatchMapping("{id}")
    
    public ResponseEntity<Product> update(
        @PathVariable long id,
        @ModelAttribute CreateProductDto data
    ){

        log.info("atualizar produto");

        return ResponseEntity.ok().body(productService.update(id, data));
    } 


    @GetMapping("{id}")
    public ResponseEntity<Product> findOne(
        @PathVariable long id
    ){

        log.info("detalhes do produto");

        return ResponseEntity.ok().body(productService.findOne(id));
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(
        @PathVariable long id
    ){

        log.info("apagar o produto");
        productService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<Page<Product>> findByCategory(
        @PathVariable ProductCategory category,
        @RequestParam(defaultValue = "0", name = "skip") int pageNumber,
        @RequestParam(defaultValue = "10", name = "size") int pageSize
    ) {
        log.info("Selecionar produtos por " + category);

        return ResponseEntity.ok().body(productService.findByCategory(pageNumber, pageSize, category));
    }
}
