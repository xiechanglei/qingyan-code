package com.xiechanglei.code.qingyan.code.vo;

import com.xiechanglei.code.qingyan.code.encryption.aes.AesEncryption;
import com.xiechanglei.code.qingyan.code.serialize.JacksonSerialize;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

public class TokenHandler implements Serializable {

    public String generateToken(Object tokenObject)
            throws UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
        return aesEncryption.encrypt(Objects.requireNonNull(JacksonSerialize.toJsonString(tokenObject)));
    }

    public <T> T parseToken(String token, Class<T> tokenClass)
            throws IllegalBlockSizeException, BadPaddingException, IOException {
        return JacksonSerialize.toObject(aesEncryption.decrypt(token), tokenClass);
    }

    private AesEncryption.AesEncryptionPassHolder aesEncryption;

    private void setSecretKey(String secretKey) {
        try {
            this.aesEncryption = AesEncryption.withPassword(secretKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TokenHandler(String secretKey) {
        this.setSecretKey(secretKey);
    }

}
