package com.roypr.microservices.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *  Just have to declare the method signature and annotate it with @RequestMapping to call the inventory service API
 *  and Spring Cloud OpenFeign will take care of the rest (like providing concrete implementation,
 *  making the REST calls converting java types into HTTP query params and such,
 *  handling the response and converting into java type, etc. )
 */
@FeignClient(value = "inventory", url="${inventory.service.url}")  //SPEL expression to read the inventory service URL from application properties, instead of hardcoding
public interface InventoryClient {
    @RequestMapping(method= RequestMethod.GET, value="/api/inventory")
    boolean isInStock(@RequestParam String skuCode,@RequestParam int quantity);
}
