package com.yanshang.car.commons;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Random;

/*
 * @ClassName PhoneCodeUtil
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2019/1/15- 16:12
 * @Version 1.0
 **/
public class PhoneCodeUtil {

    @Autowired
    private static StringRedisTemplate redisTemplate;

    @Autowired
    private void setRedisTemplate(StringRedisTemplate redisTemplate) {
        PhoneCodeUtil.redisTemplate = redisTemplate;
    }

    /**
     * 发送验证码：用于其他方法调用
     * @param phone
     * @return 发送失败是将返回空字符串，发送成功返回验证码
     */
    public static String sendCode(String phone) {
        String code = createCode();
        try {
            send(phone,code);
            String encodeCode = createEncodeCode(code);
            redisTemplate.opsForValue().set(getKey(phone),encodeCode);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return code;
    }
    /**
     * 生成验证码
     * @return
     */
    public static String createCode() {
        Random random = new Random();
        String code = "";
        for (int i =0; i<6; i++) code += random.nextInt(9);
        return  code;
    }

    /**
     * 获取加密验证码
     * @return
     */
    public static String createEncodeCode(String code) {
        if (code != null || !code.equals("")) return DigestUtils.md5Hex(code);
        return  DigestUtils.md5Hex(createCode());
    }

    /**
     * 检验验证码
     * @param phone 验证的手机号
     * @param code 未加密的验证码
     * @return
     */
    public static boolean checkCode(String phone,String code) {
        if (!CharacterUtil.checkPhone(phone)) return false;
        String newCode = encodeCode(code);
        String key = getKey(phone);
        String oldCode = redisTemplate.opsForValue().get(key);
        return newCode.equals(oldCode);
    }

    /**
     * 发送验证码：调用发送验证码方法
     * @param phone
     * @param code
     */
    private static void send(String phone,String code) {

    }

    /**
     * 获取存储key
     * @param phone 手机号
     * @return
     */
    public static String getKey(String phone) {
        String md5Hex = DigestUtils.md5Hex(phone);
        return md5Hex;
    }

    /**
     * 验证码加密
     * @param code
     * @return
     */
    public static String encodeCode(String code) {
        if (code == null) return "";
        return DigestUtils.md5Hex(code);
    }

}
