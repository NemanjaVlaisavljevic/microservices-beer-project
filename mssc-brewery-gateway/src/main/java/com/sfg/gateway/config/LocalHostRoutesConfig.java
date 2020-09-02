package com.sfg.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("!local-discovery")
@Configuration
public class LocalHostRoutesConfig {

    @Bean
    public RouteLocator localHostRoutes(RouteLocatorBuilder routeLocatorBuilder){
      return  routeLocatorBuilder
              .routes()
              .route(r -> r.path("/api/v1/beer/**" , "/api/v1/beer*")
                       .uri("http://localhost:8080")
                       .id("beer-service"))
              .route(r -> r.path("/api/v1/customers/**")
                      .uri("http://localhost:8081")
                      .id("beer-order-service"))
              .route(r -> r.path("/api/v1/beerInventory*" , "/api/v1/beerInventory/**")
              .uri("http://localhost:8082")
              .id("beer-inventory-service"))
              .build();
    }
}
