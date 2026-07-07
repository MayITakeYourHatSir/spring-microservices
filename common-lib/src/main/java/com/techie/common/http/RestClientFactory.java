package com.techie.common.http;

import com.techie.common.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class RestClientFactory {

    private final RestClient.Builder builder;
    private final SecurityUtil securityUtils;

    public RestClient create(String baseUrl) {

        ClientHttpRequestInterceptor interceptor = new BearerTokenInterceptor(securityUtils);

        return builder
                .clone()
                .baseUrl(baseUrl)
                .requestInterceptor(interceptor)
                .build();
    }

}
