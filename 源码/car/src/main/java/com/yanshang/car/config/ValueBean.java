package com.yanshang.car.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/*
 * @ClassName ValueBean
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2019/2/13- 10:49
 * @Version 1.0
 **/
@Component
public class ValueBean {

    public static String IMG_PATH;

    @Value("${basic.project.img.home}")
    public void setImgPath(String imgPath) {
        this.IMG_PATH = imgPath;
    }
}
