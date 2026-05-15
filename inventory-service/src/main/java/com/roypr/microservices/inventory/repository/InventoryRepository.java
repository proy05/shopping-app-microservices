package com.roypr.microservices.inventory.repository;

import com.roypr.microservices.inventory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    //Query Derivation: Spring Data JPA will automatically generate the query based on the method name
    boolean existsBySkuCodeAndQuantityGreaterThanEqual(String skuCode, int quantity);
}
