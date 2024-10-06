package com.backend.delivery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.delivery.models.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    
}
