package com.techie.common.config;

import com.techie.common.security.RsaKeyLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
@RequiredArgsConstructor
public class JwtDecoderConfig {

    private final RsaKeyLoader rsaKeyLoader;

    @Bean
    JwtDecoder jwtDecoder() {

        return NimbusJwtDecoder
                .withPublicKey(rsaKeyLoader.loadPublicKey())
                .build();

    }

}
