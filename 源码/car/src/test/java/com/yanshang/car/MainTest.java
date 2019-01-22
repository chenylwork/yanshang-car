package com.yanshang.car;

import com.yanshang.car.commons.BaseDao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * @ClassName MainTest
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2019/1/15- 16:15
 * @Version 1.0
 **/
public class MainTest {

    public static void main(String[] args) {
//        String str = "你是不是傻"; // 需要检测的字符串
//        String pattern  = ".*不.*"; // 正则字符串
//
//        boolean matches = Pattern.matches(pattern, str);
//        System.out.println(matches);
//        String mobile = "18132070787";
//        // "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
//        String regex = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";
//        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
//        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
//
//        while (true) {
//            try {
//                mobile = br.readLine();
//                if ("exit".equals(mobile)) return;
//                Matcher m = p.matcher(mobile);
//                boolean matches = m.matches();
//                System.out.println(matches);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
        boolean database = BaseDao.createDatabase("car", "localhost", "root", "root");
        System.out.println(database);

    }
    public static void check(Integer a,Integer b) {
        a = 100;
        b = 200;
    }
}
