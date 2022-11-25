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

/**
 * AES加密解密的通用工具类，
 * 通常对于某个项目来说，加密解密的密钥是固定的，所与对于aes的加密与解密过程来说，Cipher这个东西是不用重复生成的，一次生成后面直接使用就可以了
 * 查阅国Cipher的代码，里面的实现是线程安全的，所以对于并发下的加密解密来说，可以直接使用同一个Cipher对象
 */
public class AesEncryptor {

    private static final String DEFAULT_CHARSET = "UTF-8";


    /**
     * 将加密的的过程提前生成好，这样在加密的时候就不用每次都生成了
     * 使用AesEncryptionPassHolder来管理加密与解密的逻辑
     */
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

    /**
     * 生成SecretKey的过程封装
     */
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

    /**
     * AES 加密与解密的逻辑，密钥对应的Cipher已提前生成好了方过来了
     * 因为加密与解密的Cipher的init方法的模式不一样，所以这里将加密与解密的Cipher做成两个成员变量
     * 默认采用base64作为加密后的字符串的编码方式，这样是通用的做法
     */
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
