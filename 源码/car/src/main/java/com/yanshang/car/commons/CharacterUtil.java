package com.yanshang.car.commons;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * @ClassName CharacterUtil
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2019/1/15- 16:42
 * @Version 1.0
 **/
public class CharacterUtil {

    /**
     * 检验手机号格式
     * @param phone
     * @return
     */
    public static boolean checkPhone(String phone) {
        String regex = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    /**
     * 密码加密
     * @param password
     * @return
     */
    public static String passEncode(String password) {
        if(password == null) password = "";
        return DigestUtils.md5Hex(password);
    }

    /**
     * 字符串判断是否是空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return  str == null || str.equals("");
    }
}
