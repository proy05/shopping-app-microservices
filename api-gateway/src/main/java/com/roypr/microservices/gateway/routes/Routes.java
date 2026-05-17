package com.roypr.microservices.gateway.routes;

import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;

//Based on Spring Cloud Gateway MVC, not Spring Cloud Gateway WebFlux(which is for reactive apps)
@Configuration
public class Routes {
    @Bean
    public RouterFunction<ServerResponse> productServiceRoute(){
        return GatewayRouterFunctions.route("product_service") //returns a builder
                .route(RequestPredicates.path("/api/product"), HandlerFunctions.http("http://localhost:8080"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("productServiceCircuitBreaker", //apply CB with fallback route
                        URI.create("forward:/fallbackRoute")
                ))
                .build(); //Using Functional Endpoint Programming Model for Spring MVC, instead of annotation based programming model.
                //This is a more functional style of programming, where we define routes and handlers in a more declarative way.
    }

    @Bean
    public RouterFunction<ServerResponse> orderServiceRoute(){
        return GatewayRouterFunctions.route("order_service") //returns a builder
                .route(RequestPredicates.path("/api/order"), HandlerFunctions.http("http://localhost:8081"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("orderServiceCircuitBreaker", //apply CB with fallback route
                        URI.create("forward:/fallbackRoute")
                ))
                .build(); //Functional Endpoint Programming Model for Spring MVC
    }

    @Bean
    public RouterFunction<ServerResponse> inventoryServiceRoute(){
        return GatewayRouterFunctions.route("inventory_service") //returns a builder
                .route(RequestPredicates.path("/api/inventory"), HandlerFunctions.http("http://localhost:8082"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("inventoryServiceCircuitBreaker", //apply CB with fallback route
                        URI.create("forward:/fallbackRoute")
                ))
                .build(); //Functional Endpoint Programming Model for Spring MVC
    }

    @Bean
    public RouterFunction<ServerResponse> fallbackRoute(){
        return GatewayRouterFunctions.route("fallback")
                .GET("/fallbackRoute", request -> ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body("Service Unavailable. Please try again later."))
                .build();

    }

}
