package com.roypr.microservices.inventory.service;

import com.roypr.microservices.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public boolean isInStock(String skuCode, int quantity) {
//        return inventoryRepository.findAll()
//                .stream()
//                .anyMatch(inventory -> inventory.getSkuCode().equals(skuCode)
//                        && inventory.getQuantity() >= quantity);

        // Optimized query using Spring Data JPA method naming convention
        return inventoryRepository.existsBySkuCodeAndQuantityGreaterThanEqual(skuCode, quantity);
    }
}
