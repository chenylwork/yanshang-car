package com.yanshang.car.services;

import com.yanshang.car.bean.CarBrand;
import com.yanshang.car.bean.CarComment;
import com.yanshang.car.commons.NetMessage;
import org.springframework.web.multipart.MultipartFile;

/*
 * @ClassName CarRepository
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2019/1/16- 14:27
 * @Version 1.0
 **/
public interface CarService {

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
     * 根据汽车标识获取汽车详细信息
     * @param identity
     * @return
     */
    NetMessage getDetails(String identity);

    /**
     * 根据汽车编号，获取该汽车的评论
     * @param identity
     * @return
     */
    NetMessage getComments(String identity);

    /**
     * 对汽车进行评论
     * @param comment
     * @return
     */
    NetMessage publishComments(CarComment comment);

    /**
     * 添加汽车品牌信息
     * @param carBrand
     * @param file 品牌标志图片
     * @return
     */
    NetMessage saveBrand(CarBrand carBrand, MultipartFile file);

}
