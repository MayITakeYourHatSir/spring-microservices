package com.techie.microservices.notification.config;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.Gmail;
import com.techie.microservices.notification.util.GoogleAuthorizeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GmailConfig {

    private final GoogleAuthorizeUtil googleAuthorizeUtil;

    @Bean
    public GsonFactory gsonFactory() {

        return GsonFactory.getDefaultInstance();

    }

    @Bean
    public NetHttpTransport netHttpTransport() throws Exception {

        return GoogleNetHttpTransport.newTrustedTransport();

    }

    @Bean
    public Gmail gmail(GsonFactory gsonFactory)
            throws Exception {

        NetHttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();

        return new Gmail.Builder(
                transport,
                gsonFactory,
                googleAuthorizeUtil.getCredential()
        )
                .setApplicationName("Notification service")
                .build();

    }

}
