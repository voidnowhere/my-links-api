package com.example.gatewayservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableHystrix
@RequiredArgsConstructor
public class GatewayConfig {
    private final AuthFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("links-service", r -> r
                        .path("/api/links/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://links-service")
                ).route("auth-service", r -> r
                        .path("/api/auth/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://auth-service")
                ).build();
    }
}
