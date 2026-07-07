package com.techie.common.http;

import com.techie.common.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.client.ClientHttpRequestInterceptor;

@RequiredArgsConstructor
public class BearerTokenInterceptor implements ClientHttpRequestInterceptor {

    private final SecurityUtil securityUtils;

    @Override
    public org.springframework.http.client.ClientHttpResponse intercept(
            org.springframework.http.HttpRequest request,
            byte[] body,
            org.springframework.http.client.ClientHttpRequestExecution execution
    ) throws java.io.IOException {

        String token = securityUtils
                        .getCurrentJwt()
                        .getTokenValue();

        request.getHeaders().setBearerAuth(token);

        return execution.execute(request, body);
    }

}
