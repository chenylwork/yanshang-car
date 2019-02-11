package com.yanshang.car.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 * @ClassName WebMVCConfig
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2019/2/11- 17:14
 * @Version 1.0
 **/
@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    @Value("${basic.project.img.home}")
    private String savePath;

    @Value("${basic.project.file.visit.home}")
    private String visitPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // TODO Auto-generated method stub
        //addResourceHandler是指你想在url请求的路径
        //addResourceLocations是图片存放的真实路径
        System.out.println(savePath);
        System.out.println(visitPath);
        registry.addResourceHandler(visitPath+"/**").addResourceLocations("file:///"+savePath);
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
