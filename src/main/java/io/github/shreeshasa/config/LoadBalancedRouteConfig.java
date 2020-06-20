package io.github.shreeshasa.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author shreeshasa
 */
@Profile ("local-discovery")
@Configuration
public class LoadBalancedRouteConfig {

  @Bean
  public RouteLocator loadBalancedRoutes(RouteLocatorBuilder builder) {
    return builder.routes()
        .route("beer-service", r -> r.path("/api/v1/beer", "/api/v1/beer/*", "/api/v1/beerUpc/*")
            .uri("lb://beer-service"))
        .route("order-service", r -> r.path("/api/v1/customers", "/api/v1/customers/**")
            .uri("lb://order-service"))
        .route("inventory-service", r -> r.path("/api/v1/beer/*/inventory")
            .filters(f -> f.circuitBreaker(c -> c.setName("inventory-failover")
                .setFallbackUri("forward:/inventory-failover")
                .setRouteId("inventory-failover")))
            .uri("lb://inventory-service"))
        .route("inventory-failover-service", r -> r.path("/inventory-failover/**")
            .uri("lb://inventory-failover-service"))
        .build();
  }
}
