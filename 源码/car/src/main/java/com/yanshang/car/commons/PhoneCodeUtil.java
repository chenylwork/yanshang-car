package com.yanshang.car.commons;

import com.yanshang.car.sms.SMSUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/*
 * @ClassName PhoneCodeUtil
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2019/1/15- 16:12
 * @Version 1.0
 **/
@Component
public class PhoneCodeUtil {

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
    public static NetMessage sendCode(String phone){
        if (!CharacterUtil.checkPhone(phone)) return NetMessage.failNetMessage("","请输入正确的手机号！！");
        String code = createCode();
        NetMessage netMessage = send(phone, code);
        if (netMessage.getStatus() == NetMessage.SUCCESS) {
            String encodeCode = createEncodeCode(code);
            String key = getKey(phone);
            redisTemplate.opsForValue().set(key,encodeCode);
            redisTemplate.expire(key,TIMEOUT, TIME_TYPE);
            netMessage.setContent(code);
        }
        return netMessage;
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
    public static NetMessage checkCode(String phone,String code) {
        boolean result = false;
        if (!CharacterUtil.checkPhone(phone)) {
            return NetMessage.failNetMessage("","请输入正确的手机号！！");
        };
        String newCode = encodeCode(code);
        String key = getKey(phone);
        String oldCode = redisTemplate.opsForValue().get(key);
        if (oldCode == null || oldCode.equals("null")) {
            return NetMessage.failNetMessage("","请先获取验证码！！！");
        }
        if (result = newCode.equals(oldCode)) {
            redisTemplate.delete(key);
            return NetMessage.successNetMessage("","验证成功！！！");
        }
        return NetMessage.failNetMessage("","验证码错误！！");
    }

    /**
     * 发送验证码：调用发送验证码方法
     * @param phone
     * @param code
     */
    private static NetMessage send(String phone,String code) {
        String content = "来个车：\n 您的验证码为："+code+".\n如非本人操作请忽略本信息。";
        return SMSUtil.send(content, phone);
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

    private static final long TIMEOUT = 1000;
    private static final TimeUnit TIME_TYPE = TimeUnit.MINUTES ;

}
