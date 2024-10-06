package com.backend.delivery.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.backend.delivery.DTO.CreateStoreDto;
import com.backend.delivery.models.Store;
import com.backend.delivery.services.StoreService;
import com.backend.delivery.utils.File;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController()
@RequestMapping("api/v1/store")
@Slf4j
public class StoreController {
    
    @Autowired
    private StoreService storeService;

 
    @GetMapping
    public ResponseEntity<Page<Store>> all(
        @RequestParam(defaultValue = "0") int pageNumber,
        @RequestParam(defaultValue = "3") int pageSize
    ) {

        log.info("select stores");

        return ResponseEntity.ok().body(storeService.all(pageNumber, pageSize));
    }   

    @PostMapping
    public ResponseEntity<Store> create(@ModelAttribute @Valid CreateStoreDto data) {
        log.info("criar uma nova loja");

        return ResponseEntity.status(HttpStatus.CREATED).body(storeService.save(data));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("apagar a loja ");
        storeService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("{id}")
    @Transactional
    public ResponseEntity<Store> update(@PathVariable Long id, @ModelAttribute CreateStoreDto data) {
        log.info("Atualizar a loja");

        return ResponseEntity.ok().body(storeService.update(id, data));
    }

    @GetMapping("{id}")
    public ResponseEntity<Resource> donwload(@PathVariable Long id, HttpServletRequest request) throws IOException {
        File file = storeService.donwload(id, request);

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(file.getContentType()))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getResource().getFilename()+"\"")
            .body(file.getResource());
    }
}
