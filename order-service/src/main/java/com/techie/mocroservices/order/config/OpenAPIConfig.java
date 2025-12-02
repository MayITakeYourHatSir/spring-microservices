package com.techie.mocroservices.order.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Value("${openapi.server.url}")
    private String serverUrl;

    @Bean
    public OpenAPI OrderServiceAPI() {
        return new OpenAPI()
                .servers(List.of(new Server().url(serverUrl).description("API Gateway")))
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Order Service API")
                        .version("1.0.0")
                        .description("API documentation for Order Service"));
    }

}
