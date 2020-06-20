package io.github.shreeshasa.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author shreeshasa
 */
//@Profile ("google")
//@Configuration
public class GoogleConfig {

  @Bean
  public RouteLocator googleRoutes(RouteLocatorBuilder builder) {
    return builder.routes()
        .route(r -> r.path("/googlesearch").uri("https://google.com"))
        .build();
  }
}
