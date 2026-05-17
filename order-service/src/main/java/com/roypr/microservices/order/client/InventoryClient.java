package com.roypr.microservices.order.client;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

/**
 * Removed OpenFeign as it is not supported in Spring Boot 3 and using HTTP Interface/HTTP Service Client
 */
public interface InventoryClient {

    @GetExchange("/api/inventory")
    boolean isInStock(@RequestParam String skuCode,@RequestParam int quantity);
}
