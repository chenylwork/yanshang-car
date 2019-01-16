package com.yanshang.car;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Random;

/*
 * @ClassName MainTest
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2019/1/15- 16:15
 * @Version 1.0
 **/
public class MainTest {

    public static void main(String[] a) {
        //50a2ae61ce1b9c20fefa99223021eaf9
        //50a2ae61ce1b9c20fefa99223021eaf9
        //50a2ae61ce1b9c20fefa99223021eaf9
        String code = null;
        String md5Hex = DigestUtils.md5Hex(code);
        System.out.println(md5Hex);
    }
}
