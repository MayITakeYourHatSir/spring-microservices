package com.techie.common.http;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.client.support.RestClientAdapter;

@Component
@RequiredArgsConstructor
public class HttpServiceFactory {

    private final RestClientFactory restClientFactory;

    public <T> T createClient(Class<T> type, String baseUrl) {

        RestClient restClient = restClientFactory.create(baseUrl);

        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(restClient))
                .build();

        return factory.createClient(type);
    }

}
