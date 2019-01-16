package com.yanshang.car.commons;

import java.util.ArrayList;

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
        return false;
    }

    /**
     * 密码加密
     * @param password
     * @return
     */
    public static String passEncode(String password) {
        return password;
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
