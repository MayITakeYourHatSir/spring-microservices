package com.techie.microservices.order.config;

import com.techie.common.http.HttpServiceFactory;
import com.techie.microservices.order.client.InventoryClient;
import com.techie.microservices.order.client.NotificationClient;
import com.techie.microservices.order.client.ProductClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class HttpClientConfig {

    private final HttpServiceFactory httpServiceFactory;

    @Value("${inventory.url}")
    private String inventoryServiceUrl;

    @Value("${product.url}")
    private String productServiceUrl;

    @Value("${notification.url}")
    private String notificationServiceUrl;

    @Bean
    public InventoryClient inventoryClient(){
        return httpServiceFactory.createClient(
                InventoryClient.class,
                inventoryServiceUrl
        );
    }

    @Bean
    public ProductClient productClient(){
        return httpServiceFactory.createClient(
                ProductClient.class,
                productServiceUrl
        );
    }

    @Bean
    public NotificationClient notificationClient(){
        return httpServiceFactory.createClient(
                NotificationClient.class,
                notificationServiceUrl
        );
    }

}
