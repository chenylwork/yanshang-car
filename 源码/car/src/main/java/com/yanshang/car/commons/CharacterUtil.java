package com.yanshang.car.commons;

import com.yanshang.car.util.SnowFlake;
import org.apache.commons.codec.digest.DigestUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    private int referrerCodeLength = 8;


    /**
     * 生成订单编号
     * @return
     */
    public static String orderCode() {
        return new SnowFlake(2, 3).nextId()+"";
    }
    /**
     * 生成推荐码
     * @param num
     * @return
     */
    public static String referrerCode(String num) {
        int supLength = 8;
        StringBuilder stringBuilder = new StringBuilder();
        if (!CharacterUtil.isEmpty(num)) supLength = supLength - num.length();
        for (int i=0; i<supLength; i++) stringBuilder.append("0");
        return stringBuilder.append(num).toString();
    }
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

    /**
     * 获取系统当前时间
     * @return
     */
    public static String dataTime() {
        String pattern = YEAR + "-" + MONTH + "-" + DAY + " "+ HOUR + ":" + MINUTE + ":" + SECOND + " " + MILLISECOND;
        return new SimpleDateFormat(pattern).format(new Date());
    }
    public static String data() {
        return dataTime().substring(0,10);
    }
    public static String time() {
        return dataTime().substring(0,10);
    }
    public static String year() {
        return dataTime().substring(0,4);
    }
    public static String month() {
        return dataTime().substring(5,7);
    }
    public static String day() {
        return dataTime().substring(8,10);
    }
    public static String hour() {
        return dataTime().substring(11,13);
    }
    public static String minute() {
        return dataTime().substring(14,16);
    }
    public static String second() {
        return dataTime().substring(17,19);
    }
    public static String millisecond() {
        return dataTime().substring(20,23);
    }

    private static final String YEAR = "yyyy"; // 年
    private static final String MONTH = "MM"; // 月
    private static final String DAY = "dd"; // 日
    private static final String HOUR = "HH"; // 时
    private static final String MINUTE = "mm"; // 分
    private static final String SECOND = "ss"; // 秒
    private static final String MILLISECOND = "SSS"; // 毫秒
}
