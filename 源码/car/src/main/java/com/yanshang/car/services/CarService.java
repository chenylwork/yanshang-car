package com.yanshang.car.services;

import com.yanshang.car.bean.Car;
import com.yanshang.car.bean.CarBrand;
import com.yanshang.car.bean.CarComment;
import com.yanshang.car.commons.NetMessage;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

/*
 * @ClassName CarRepository
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2019/1/16- 14:27
 * @Version 1.0
 **/
public interface CarService {

    String MONGODB_CAR_COLLECTION_NAME = "car";

    /**
     * 获取全部汽车品牌
     * @return
     */
    NetMessage getAllCarBrand() ;

    /**
     * 根据汽车品牌获取主打汽车信息，如果没有汽车品牌参数将获取全部主打汽车信息
     * @param brand 汽车品牌，
     * @return
     */
    NetMessage getRecommend(String brand);

    /**
     * 保存汽车基本信息
     * @param data 录入汽车信息
     * @return
     */
    NetMessage saveInfo(String data);

    /**
     * 评论汽车
     * @param carComment
     * @return
     */
    NetMessage publishComments(CarComment carComment);

    /**
     * 分页获取汽车评论信息
     * @param carid 汽车编号
     * @param start 开始记录
     * @param end 结束记录
     * @return
     */
    NetMessage getComments(String carid,int start,int end);

    /**
     * 获取汽车基本信息
     * @param identity 汽车标识
     * @return
     */
    NetMessage getInfo(String identity);
    /**
     * 根据汽车标识获取汽车详细配置信息
     * @param identity
     * @return
     */
    NetMessage getDetails(String identity);

    /**
     * 添加汽车品牌信息
     * @param carBrand
     * @param file 品牌标志图片
     * @return
     */
    NetMessage saveBrand(CarBrand carBrand, MultipartFile file);

}
