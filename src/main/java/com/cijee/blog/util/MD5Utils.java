package com.cijee.blog.util;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author cijee
 * @date 2020/6/27
 */
@Slf4j
public class MD5Utils {

    /**
     * MD5加密
     * @param str 原字符串
     * @return 加密后的字符串
     */
    public static String string2MD5(String str) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            log.info(e.toString());
            e.printStackTrace();
            return null;
        }
        md5.update(str.getBytes());
        byte[] byteDigest = md5.digest();
        for (byte b : byteDigest) {
            System.out.print(b);
        }
        System.out.println();
        int i;
        StringBuffer sb = new StringBuffer("");
        for (int offset = 0; offset < byteDigest.length; offset++) {
            i = byteDigest[offset];
            if (i < 0)
                i += 256;
            if (i < 16)
                sb.append("0");
            sb.append(Integer.toHexString(i));
        }
        // 32位加密
        return sb.toString();
    }
}