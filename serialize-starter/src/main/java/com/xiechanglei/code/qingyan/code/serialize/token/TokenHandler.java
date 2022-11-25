package com.xiechanglei.code.qingyan.code.serialize.token;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xiechanglei.code.qingyan.code.encryption.aes.AesEncryptor;
import com.xiechanglei.code.qingyan.code.serialize.json.JacksonSerializer;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

public class TokenHandler implements Serializable {

    public String generateToken(Object tokenObject)
            throws UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, JsonProcessingException {
        return aesEncryption.encrypt(Objects.requireNonNull(JacksonSerializer.toJsonString(tokenObject)));
    }

    public <T> T parseToken(String token, Class<T> tokenClass)
            throws IllegalBlockSizeException, BadPaddingException, IOException {
        return JacksonSerializer.toObject(aesEncryption.decrypt(token), tokenClass);
    }

    private AesEncryptor.AesEncryptionPassHolder aesEncryption;

    private void setSecretKey(String secretKey) {
        try {
            this.aesEncryption = AesEncryptor.withPassword(secretKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TokenHandler(String secretKey) {
        this.setSecretKey(secretKey);
    }

}
