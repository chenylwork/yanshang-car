package com.yanshang.car.services;

import com.yanshang.car.bean.*;
import com.yanshang.car.commons.NetMessage;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/*
 * @ClassName CarRepository
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2019/1/16- 14:27
 * @Version 1.0
 **/
public interface CarService {

    String MONGODB_CAR_COLLECTION_NAME = "car";
    String MONGODB_CAR_PRICE_COLLECTION_NAME = "car_price";
//    String MONGODB_CAR_IMAGE_COLLECTION_NAME = "car_image";

    String LABEL_RECOMMEND = "0201"; // 推荐标签
    String LABEL_CALL = "0202"; // 挂靠标签
    String LABEL_RECYCLE= "0203"; // 回收标签

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
    @Deprecated
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
     * 获取汽车集合
     * @param data
     * @return
     */
    NetMessage getInfos(HashMap<String,String> data);

    /**
     * 获取汽车类型标签
     * @return
     */
    NetMessage getTypes();
    /**
     * 获取汽车详细配置信息
     * @param data
     * @return
     */
    NetMessage getDetails(Map<String,String> data);

    /**
     * 添加汽车品牌信息
     * @param carBrand
     * @param file 品牌标志图片
     * @return
     */
    NetMessage saveBrand(CarBrand carBrand, MultipartFile file);

    /**
     * 汽车购物车，加入商品
     * @param carCart
     * @return
     */
    NetMessage saveCarCart(CarCart carCart);

    /**
     * 根据用户编号获取购物车信息
     * @param userid
     * @return
     */
    NetMessage getCarCart(String userid);

    /**
     * 设置汽车标签
     * @param carid
     * @param label
     * @return
     */
    NetMessage saveLabel(String carid,String label);

    /**
     * 上传汽车颜色对应的照片
     * @param carid
     * @param color
     * @param file
     * @return
     */
    NetMessage upColors(String carid,String color,MultipartFile file);

    /**
     * 获取汽车图片
     * @param carid
     * @return
     */
    @Deprecated
    NetMessage getImages(String carid);

    /**
     * 上传汽车图片
     * @param carid
     * @param file
     * @return
     */
    NetMessage upImage(String carid,MultipartFile... file);
    /**
     * 录入汽车价钱信息
     * @param carid 汽车编号
     * @param data json数据
     * @return
     */
    NetMessage saveCarPrice(String carid,String data);

    /**
     * 获取汽车价钱信息
     * @param carid 汽车编号
     * @return
     */
    @Deprecated
    NetMessage getCarPrice(String carid);

    /**
     * 添加租车订单
     * @param rentOrder
     * @return
     */
    NetMessage addRentOrder(RentOrder rentOrder);

    /**
     * 获取租车订单
     * @param rentOrder
     * @return
     */
    NetMessage getRentOrder(RentOrder rentOrder);

    /**
     * 添加汽车订单
     * @param testOrder
     * @return
     */
    NetMessage saveTestOrder(TestOrder testOrder);

    /**
     * 获取汽车订单信息
     * @return
     */
    NetMessage getTestOrders(TestOrder testOrder);

    /**
     * 保存关注的汽车
     * @param carFans
     * @return
     */
    NetMessage saveCarFans(CarFans carFans);

    /**
     * 获取我的关注车型
     * @param accountid
     * @return
     */
    NetMessage getCarFans(String accountid);

}
