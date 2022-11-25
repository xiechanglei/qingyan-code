package com.xiechanglei.code.qingyan.code.encryption.md5;

import com.xiechanglei.code.qingyan.code.aids.bytes.ByteAids;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5签名工具类
 */
public class Md5Digest {

    public static String get(String str) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(str.getBytes());
        byte[] digest = md5.digest();
        return ByteAids.byte2hex(digest);
    }

    public static String get16(String str) throws NoSuchAlgorithmException {
        return get(str).substring(8, 24);
    }


    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println(get16("123456"));
        System.out.println(get("123456"));
    }
}
