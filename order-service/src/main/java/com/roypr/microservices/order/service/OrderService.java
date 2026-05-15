package com.roypr.microservices.order.service;

import com.roypr.microservices.order.client.InventoryClient;
import com.roypr.microservices.order.dto.OrderRequest;
import lombok.RequiredArgsConstructor;
import com.roypr.microservices.order.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.roypr.microservices.order.repository.OrderRepository;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

//    public OrderService(OrderRepository orderRepository) {
//        this.orderRepository = orderRepository;
//    }

    public void placeOrder(OrderRequest orderRequest) {
        // map orderRequest to Order entity
        // save order to database using OrderRepository

        //Mockito:
        // It intercepts a method call within your application and returns a hardcoded value without ever leaving the Java Virtual Machine (JVM).
        // only the method is mocked,
        //Wiremock: actual HTTP api is mocked.
        // It spins up a tiny HTTP server. Your application sends a real HTTP request (GET, POST, etc.), and WireMock sends back a real HTTP response.
        var isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());

        if (isProductInStock) {
            Order order = new Order(null,
                    UUID.randomUUID().toString(),
                    orderRequest.skuCode(),
                    orderRequest.price(),
                    orderRequest.quantity());
            log.info("Logging:{}", order.toString());

            orderRepository.save(order);
        }
        else {
            throw new RuntimeException("Product with SKU code " + orderRequest.skuCode() + " is not in stock");
        }

    }
}
