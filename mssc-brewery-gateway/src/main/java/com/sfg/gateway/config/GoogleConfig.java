package com.sfg.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

//@Profile("google")
//@Configuration
public class GoogleConfig {

    @Bean
    public RouteLocator googleConfiguration(RouteLocatorBuilder builder){
        return builder.routes()
                .route(r -> r.path("/synapse")
                .uri("https://youtube.com")
                .id("youtube"))
                .build();
    }
}
