package com.techie.microservices.notification.util;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.GmailScopes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;

@Component
public class GoogleAuthorizeUtil {

    @Value("${gmail.credentials-path}")
    private String credentialsPath;

    @Value("${gmail.tokens-directory}")
    private String tokensDirectory;

    private static final List<String> SCOPES = List.of(GmailScopes.GMAIL_SEND);

    private static final GsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    public Credential getCredential() throws Exception {

        NetHttpTransport transport = getTransport();

        GoogleAuthorizationCodeFlow flow = createFlow(transport);

        return authorize(flow);

    }

    private NetHttpTransport getTransport() throws Exception {
        return GoogleNetHttpTransport.newTrustedTransport();
    }

    private GoogleClientSecrets loadClientSecrets(NetHttpTransport transport)
            throws IOException {

        InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream(credentialsPath);

        if (inputStream == null) {
            throw new FileNotFoundException("credentials.json not found");
        }

        return GoogleClientSecrets.load(
                JSON_FACTORY,
                new InputStreamReader(inputStream)
        );
    }

    private GoogleAuthorizationCodeFlow createFlow(NetHttpTransport transport) throws Exception {

        GoogleClientSecrets clientSecrets = loadClientSecrets(transport);

        return new GoogleAuthorizationCodeFlow.Builder(
                transport,
                JSON_FACTORY,
                clientSecrets,
                SCOPES
        )
                .setDataStoreFactory(
                        new FileDataStoreFactory(
                                new File(tokensDirectory)
                        )
                )
                .setAccessType("offline")
                .build();
    }

    private Credential authorize(GoogleAuthorizationCodeFlow flow) throws Exception {

        return new AuthorizationCodeInstalledApp(
                flow,
                new LocalServerReceiver.Builder()
                        .setPort(8888)
                        .build()
        ).authorize("user");

    }

}
