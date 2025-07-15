package com.bash.Unitrack.Utilities;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Component
public class RSAKeyProperties {

    public RSAPublicKey publicKey;
    public RSAPrivateKey privateKey;


    public RSAKeyProperties() throws NoSuchAlgorithmException {
        KeyPair keyPair = KeyGeneratorUtility.keyGenerator();

        this.privateKey = (RSAPrivateKey) keyPair.getPrivate();

        this.publicKey = (RSAPublicKey) keyPair.getPublic();
    }


    public RSAPublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(RSAPublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public RSAPrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(RSAPrivateKey privateKey) {
        this.privateKey = privateKey;
    }
}
