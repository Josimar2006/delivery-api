package com.backend.delivery.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import jakarta.servlet.http.HttpServletRequest;
import com.backend.delivery.DTO.CreateStoreDto;
import com.backend.delivery.models.Store;
import com.backend.delivery.repositories.StoreRepository;
import com.backend.delivery.utils.File;

@Service
public class StoreService {
    
    @Autowired
    StoreRepository storeRepository;

    @Autowired
    FileService fileService;

    public Store save(CreateStoreDto data) {
        String filename = fileService.upload(data.store_image());

        Store store = Store.builder()
            .store_name(data.store_name())
            .rating(data.rating())
            .category(data.category())
            .store_image(filename)
            .build();
        
        return storeRepository.save(store);
    }

    public void delete(Long id) {

        Store store = storeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Store Not found"));

        storeRepository.delete(store);
    }

    public Store update(Long id, CreateStoreDto data) {
        Store store = storeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Store Not found"));

        String filename = fileService.upload(data.store_image());

        store.setCategory(data.category());
        store.setStore_name(data.store_name());
        store.setRating(data.rating());
        store.setStore_image(filename);

        return storeRepository.save(store);
    }

    public File donwload(long id, HttpServletRequest request) throws IOException {
        Store store = storeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Store Not found"));

        return fileService.donwload(store.getStore_image(), request);
        
    }

    public Page<Store> all(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return storeRepository.findAll(pageable);
    }
}
