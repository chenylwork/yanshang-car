/*
 * PROJECT NAME: openplatform
 * CREATED TIME: 15-4-30 下午4:16
 *       AUTHOR: lizhiming
 *    COPYRIGHT: Copyright(c) 2015~2020 All Rights Reserved.
 *
 */
package com.yanshang.car.sms;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.logging.Logger;


public class MD5Util {

    private static Logger log = Logger.getLogger("MD5Util.class");
    
    private static final Charset charset = Charset.forName("utf-8");

    /**
     * MD5计算
     *
     * @param str
     * @return
     */
    public static String md5Crypt(String str) {
        return md5Crypt(str.getBytes(charset));
    }
    
    /**
     * MD5散列(默认小写字符)
     *
     * @param keyBytes
     * @return
     */
    public static String md5Crypt(byte[] keyBytes) {
        return md5Crypt(keyBytes, true);
    }

    /**
     * MD5散列
     *
     * @param keyBytes
     * @return
     */
    public static String md5Crypt(byte[] keyBytes, boolean toLowerCase) {
        try {
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");

            // 使用指定的字节更新摘要
            mdInst.update(keyBytes);

            // 获得密文
            byte[] md = mdInst.digest();

            // 把密文转换成十六进制的字符串形式
            return HexUtil.encodeHexString(toLowerCase, md);
        } catch (Exception e) {
            log.info(String.format("md5Crypt meet exception:%s, input:%s", e.getLocalizedMessage(), new String(keyBytes)));
        }

        return "";
    }




}
