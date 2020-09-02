package com.sfg.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("local-discovery")
public class LoadBalanceRoutes {

    @Bean
    public RouteLocator loadBalanceRoutesFromMicroservices(RouteLocatorBuilder routeLocatorBuilder){
        return  routeLocatorBuilder
                .routes()
                .route(r -> r.path("/api/v1/beer/**" , "/api/v1/beer*")
                        .uri("lb://beer-service")
                        .id("beer-service"))
                .route(r -> r.path("/api/v1/customers/**")
                        .uri("lb://beer-order-service")
                        .id("beer-order-service"))
                .route(r -> r.path("/api/v1/beerInventory*" , "/api/v1/beerInventory/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName("inventoryCB")
                                .setFallbackUri("forward:/inventory-failover")
                                .setRouteId("inv-failover")
                        ))
                        .uri("lb://beer-inventory-service")
                        .id("beer-inventory-service"))
                .route(r -> r.path("/inventory-failover*")
                        .uri("lb://inventory-failover")
                        .id("inventory-failover"))

                .build();
    }
}
