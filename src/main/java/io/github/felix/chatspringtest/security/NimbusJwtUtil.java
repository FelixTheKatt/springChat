package io.github.felix.chatspringtest.security;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.stereotype.Component;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

@Component
public class NimbusJwtUtil {
    private final RSAPrivateKey privateKey;
    private final RSAPublicKey publicKey;

    public NimbusJwtUtil() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        this.privateKey = (RSAPrivateKey) keyPair.getPrivate();
        this.publicKey = (RSAPublicKey) keyPair.getPublic();
    }

    public String generateToken(String username) throws JOSEException {
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("chat-spring-test")
                .expirationTime(new Date(new Date().getTime() + 60 * 60 * 1000)) // 1 heure
                .build();

        JWSHeader header = new JWSHeader(JWSAlgorithm.RS256);
        SignedJWT signedJWT = new SignedJWT(header, claimsSet);

        signedJWT.sign(new RSASSASigner(privateKey));

        return signedJWT.serialize();
    }

    public boolean validateToken(String token) throws JOSEException, java.text.ParseException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWSVerifier verifier = new RSASSAVerifier(publicKey);
        return signedJWT.verify(verifier) && !isTokenExpired(signedJWT);
    }

    private boolean isTokenExpired(SignedJWT signedJWT) throws java.text.ParseException {
        Date expiration = signedJWT.getJWTClaimsSet().getExpirationTime();
        return expiration.before(new Date());
    }

    public String extractUsername(String token) throws java.text.ParseException {
        return SignedJWT.parse(token).getJWTClaimsSet().getSubject();
    }
}
