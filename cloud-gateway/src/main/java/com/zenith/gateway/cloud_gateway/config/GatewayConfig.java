package com.zenith.gateway.cloud_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;

import static org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions.lb;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.path;
import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
@Configuration
public class GatewayConfig {

    @Bean
    public RouterFunction<ServerResponse> catalogRoute() {
        return route("catalog-service")
                .route(path("/v1/products/**"),http()) // Use empty http()
                .filter(lb("CATALOG-SERVICE"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> inventoryRoute() {
        return route("inventory-service")
                .route(path("/v1/inventory/**"), http())
                .filter(lb("INVENTORY-SERVICE"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> orderRoute() {
        return route("order-service")
                .route(path("/v1/order/**"), http())
                .filter(lb("ORDER-SERVICE"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> recommendationRoute() {
        return route("recommendation-service")
                .route(path("/v1/recommendations/**"), http())
                .filter(lb("RECOMMENDATION-SERVICE"))
                .build();
    }
}
