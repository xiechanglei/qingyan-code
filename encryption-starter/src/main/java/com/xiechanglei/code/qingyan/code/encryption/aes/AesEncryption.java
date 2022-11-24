package com.xiechanglei.code.qingyan.code.encryption.aes;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AesEncryption {

    private static final String DEFAULT_CHARSET = "UTF-8";

    public static AesEncryptionPassHolder withPassword(String password) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {

        SecretKey secretKey = buildSecretKeyWithPass(password);

        AesEncryptionPassHolder aesPassHolder = new AesEncryptionPassHolder();

        Cipher encryptCipher = Cipher.getInstance("AES");
        encryptCipher.init(Cipher.ENCRYPT_MODE,secretKey);
        aesPassHolder.encryptCipher = encryptCipher;

        Cipher decryptCipher = Cipher.getInstance("AES");
        decryptCipher.init(Cipher.DECRYPT_MODE, secretKey);
        aesPassHolder.decryptCipher = decryptCipher;

        return aesPassHolder;
    }

    private static SecretKey buildSecretKeyWithPass(String password) throws NoSuchAlgorithmException {
        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(password.getBytes());
        keygen.init(128, secureRandom);
        SecretKey original_key = keygen.generateKey();
        byte[] raw = original_key.getEncoded();
        SecretKey key = new SecretKeySpec(raw, "AES");
        return key;
    }

    public static class AesEncryptionPassHolder {
        Cipher encryptCipher;
        Cipher decryptCipher;
        BASE64Decoder decoder = new BASE64Decoder();
        BASE64Encoder encoder = new BASE64Encoder();

        public String encrypt(String content) throws UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
            byte[] byte_encode = content.getBytes(DEFAULT_CHARSET);
            byte[] byte_AES = encryptCipher.doFinal(byte_encode);
            return encoder.encode(byte_AES);
        }

        public String decrypt(String content) throws IllegalBlockSizeException, BadPaddingException, IOException {
            byte[] byte_content = decoder.decodeBuffer(content);
            byte[] byte_decode = decryptCipher.doFinal(byte_content);
            return new String(byte_decode, DEFAULT_CHARSET);
        }
    }

}
