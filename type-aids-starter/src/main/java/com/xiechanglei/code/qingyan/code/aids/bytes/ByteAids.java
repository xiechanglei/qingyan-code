package com.xiechanglei.code.qingyan.code.aids.bytes;

public class ByteAids {

    /**
     * 字节数组转16进制字符串
     */
    public static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stamp;
        for (int n = 0; b != null && n < b.length; n++) {
            stamp = Integer.toHexString(b[n] & 0XFF);
            hs.append(stamp.length() == 1 ? 0 + stamp : stamp);
        }
        return hs.toString().toLowerCase();
    }
}
