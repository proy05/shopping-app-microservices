package com.roypr.microservices.order.controller;

import com.roypr.microservices.order.dto.OrderRequest;
import com.roypr.microservices.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
        // call orderService to place order
        orderService.placeOrder(orderRequest);
        return "Order placed successfully";
    }

}
