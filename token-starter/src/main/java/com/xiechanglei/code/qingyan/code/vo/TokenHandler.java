package com.xiechanglei.code.qingyan.code.vo;

import com.xiechanglei.code.qingyan.code.encryption.aes.AesEncryption;
import com.xiechanglei.code.qingyan.code.serialize.JacksonSerialize;
import lombok.RequiredArgsConstructor;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 *
 */
@RequiredArgsConstructor
public class TokenHandler implements Serializable {
    private final String secretKey;


    public void generateToken(Object tokenObject) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
        AesEncryption.withPassword(secretKey).encrypt(Objects.requireNonNull(JacksonSerialize.toJsonString(tokenObject)));
    }

    public void parseToken(String token, Class<?> tokenClass) {


    }
}
