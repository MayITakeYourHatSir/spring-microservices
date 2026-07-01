package com.techie.common.security;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class RsaKeyLoader {

    private static final String PRIVATE_KEY_PATH = "keys/private.pem";
    private static final String PUBLIC_KEY_PATH = "keys/public.pem";

    public RSAPrivateKey loadPrivateKey() {

        try {

            String key = readKey(PRIVATE_KEY_PATH)
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", "");

            byte[] decoded = Base64.getDecoder().decode(key);

            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            return (RSAPrivateKey) keyFactory.generatePrivate(spec);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load private key.", e);
        }
    }

    public RSAPublicKey loadPublicKey() {

        try {

            String key = readKey(PUBLIC_KEY_PATH)
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s", "");

            byte[] decoded = Base64.getDecoder().decode(key);

            X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            return (RSAPublicKey) keyFactory.generatePublic(spec);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load public key.", e);
        }
    }

    private String readKey(String path) {

        try (InputStream inputStream = new ClassPathResource(path).getInputStream()) {

            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        } catch (Exception e) {
            throw new RuntimeException("Cannot read key file : " + path, e);
        }
    }

}
