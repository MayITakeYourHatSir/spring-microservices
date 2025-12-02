package com.programming.techie.gateway.routes;

import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.rewritePath;
import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

@Configuration
public class Routes {

    @Bean
    public RouterFunction<ServerResponse> productServiceRoute(){
        return GatewayRouterFunctions.route("product-service")
                .route(RequestPredicates.path("/api/products/**"), http())
                .before(uri("http://localhost:8080"))

                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> productServiceSwaggerRoute(){
        return GatewayRouterFunctions.route("product-service-swagger")
                .route(RequestPredicates.path("/aggregate/product-service/v3/api-docs"), http())
                .before(uri("http://localhost:8080"))
                .before(rewritePath("/aggregate/product-service", ""))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> orderServiceRoute(){
        return GatewayRouterFunctions.route("order-service")
                .route(RequestPredicates.path("/api/order/**"), http())
                .before(uri("http://localhost:8081"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> orderServiceSwaggerRoute(){
        return GatewayRouterFunctions.route("order-service-swagger")
                .route(RequestPredicates.path("/aggregate/order-service/v3/api-docs"), http())
                .before(uri("http://localhost:8081"))
                .before(rewritePath("/aggregate/order-service", ""))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> inventoryServiceRoute(){
        return GatewayRouterFunctions.route("inventory-service")
                .route(RequestPredicates.path("/api/inventory/**"), http())
                .before(uri("http://localhost:8082"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> inventoryServiceSwaggerRoute(){
        return GatewayRouterFunctions.route("inventory-service-swagger")
                .route(RequestPredicates.path("/aggregate/inventory-service/v3/api-docs"), http())
                .before(uri("http://localhost:8082"))
                .before(rewritePath("/aggregate/inventory-service", ""))
                .build();
    }

}
