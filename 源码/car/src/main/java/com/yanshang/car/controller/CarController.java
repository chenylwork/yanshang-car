package com.yanshang.car.controller;

import com.yanshang.car.bean.*;
import com.yanshang.car.commons.MPage;
import com.yanshang.car.commons.NetMessage;
import com.yanshang.car.services.CarService;
import com.yanshang.car.services.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.nio.ch.Net;

import java.util.HashMap;
import java.util.Map;

/*
 * @ClassName CarRepository
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2019/1/16- 14:27
 * @Version 1.0
 **/
@RestController
@RequestMapping("/car")
public class CarController {

    @Autowired
    private CarService carService;
    /**
     * 录入新汽车信息
     * @param car 汽车信息
     * @return
     */
    @RequestMapping("/add")
    public NetMessage add(Car car) {
        return null;
    }

    /**
     * 获取主打汽车
     * 如果没有汽车品牌，将获取全部的主打车型
     * @param brand 汽车品牌
     * @return
     */
    @RequestMapping("/recommend")
    public NetMessage recommend(@RequestParam(value = "brand",defaultValue = "") String brand) {
        return carService.getRecommend(brand);
    }

    /**
     * 保存汽车基本信息
     * @param data
     * @return ,MultipartFile... file
     */
    @RequestMapping("/save/info")
    public NetMessage saveInfo(String data) {
        return carService.saveInfo(data);
    }

    /**
     * 保存汽车配置信息
     * @param carid 汽车编号
     * @param name 汽车配置名称
     * @param file 汽车配置文件
     * @return
     */
    @RequestMapping("/save/details")
    public NetMessage saveDetails(String carid,String name,MultipartFile file) {
        return carService.saveDetails(carid,name,file);
    }

    @RequestMapping("/save/label")
    public NetMessage saveLabel(String carid,String label) {
        return carService.saveLabel(carid,label);
    }
    /**
     * 根据汽车标识获取汽车基本信息
     * @param identity 汽车标识
     * @return
     */
    @RequestMapping("/info")
    public NetMessage info(String identity) {
        return carService.getInfo(identity);
    }

    @RequestMapping("/infos")
    public NetMessage infos(@RequestParam HashMap<String,String> data) {
        return carService.getInfos(data);
    }
    /**
     * 获取汽车详细配置信息
     * @param identity 汽车标识
     * @return
     */
    @RequestMapping("/details")
    public NetMessage details(@RequestParam HashMap<String,String> identity) {
        return carService.getDetails(identity);
    }


    /**
     * 获取全部汽车品牌
     * @return
     */
    @RequestMapping("/brands")
    public NetMessage brands() {
        return carService.getAllCarBrand();
    }


    /**
     * 获取汽车图片：
     * @param carid
     * @return
     */
    @RequestMapping("/image")
    public NetMessage images(String carid) {
        return carService.getImages(carid);
    }

    /**
     * 上传汽车对应颜色照片
     * @param color
     * @param file
     * @return
     */
    @RequestMapping("/colors/up")
    public NetMessage saveColors(String carid,String color,MultipartFile file) {
        return carService.upColors(carid,color,file);
    }

    /**
     * 上传汽车颜色
     * @param carid 汽车编号
     * @param file 汽车图片集合
     * @return
     */
    @RequestMapping("/image/up")
    public NetMessage saveImage(String carid,MultipartFile... file) {
        return carService.upImage(carid,file);
    }


    /**
     * 获取汽车类型
     * @return
     */
    @RequestMapping("/types")
    public NetMessage types() {
        return carService.getTypes();
    }

    /**
     * 获取某汽车的评论信息
     * @param carid
     * @return
     */
    @RequestMapping("/comments")
    public NetMessage comments(String carid,int start,int end) {
        return carService.getComments(carid,start,end);
    }

    /**
     * 对汽车进行评论发表
     * @param comment
     * @return
     */
    @RequestMapping("/comment/publish")
    public NetMessage publishComments(CarComment comment) {
        return carService.publishComments(comment);
    }

    /**
     * 汽车加入购物车
     * @param carCart
     * @return
     */
    @RequestMapping("/cart/save")
    public NetMessage shoppingCar(CarCart carCart) {
        return carService.saveCarCart(carCart);
    }

    /**
     * 根据用户编号获取购物车信息
     * @param userid
     * @return
     */
    @RequestMapping("/cart/get")
    public NetMessage getCarts(String userid) {
        return carService.getCarCart(userid);
    }

    /**
     * 保存汽车价钱信息
     * @param data
     * @return
     */
    @RequestMapping("/price/save/{carid}")
    public NetMessage savePrice(@PathVariable("carid") String carid,@RequestParam Map<String,String> data) {
        return carService.saveCarPrice(carid,data);
    }
    /**
     * 获取汽车价钱信息
     * @param carid
     * @return
     */
    @RequestMapping("/price/get")
    public NetMessage getPrice(String carid) {
        return carService.getCarPrice(carid);
    }

    /**
     * 保存购车订单
     * @param carOrder
     * @return
     */
    @RequestMapping("/order/save")
    public NetMessage saveCarOrder(CarOrder carOrder){
        return carService.saveCarOrder(carOrder);
    }

    /**
     * 获取购车订单
     * @param carOrder
     * @param page
     * @return
     */
    @RequestMapping("/order/get")
    public NetMessage getCarOrder(CarOrder carOrder, MPage<CarOrder> page){
        return carService.getCarOrder(carOrder,page);
    }

    /**
     * 获取用户的关于汽车的订单
     * @param accountid
     * @return
     */
    public NetMessage getUserCarOrder(String accountid) {
        return null;
    }
    /**
     * 根据汽车唯一标识获取该汽车的贷款信息
     * @param identity 汽车唯一标识
     * @return
     */
    @RequestMapping("/loan")
    public NetMessage loan(String identity) {
        return null;
    }

    /**
     * 获取全部可以出租的汽车
     * @param identity
     * @return
     */
    @RequestMapping("/rents")
    public NetMessage rents(String identity) {
        return null;
    }

    /**
     * 获取租车信息
     * @param order
     * @return
     */
    @RequestMapping("/rent")
    public NetMessage rent(RentOrder order) {
        return carService.getRentOrder(order);
    }

    /**
     * 添加租车订单
     * @param order 订单信息
     * @return
     */
    @RequestMapping("/order/rent/save")
    public NetMessage saveRentOrder(RentOrder order) {
        return carService.addRentOrder(order);
    }

    /**
     * 获取试驾信息
     * @param order
     * @return
     */
    @RequestMapping("/test")
    public NetMessage test(TestOrder order) {
        return carService.getTestOrders(order);
    }

    /**
     * 添加试驾订单
     * @param order
     * @return
     */
    @RequestMapping("/order/test/save")
    public NetMessage saveTestOrder(TestOrder order) {
        return carService.saveTestOrder(order);
    }

    /**
     * 保存关注车型
     * @param carFans
     * @return
     */
    @RequestMapping("/fans/save")
    public NetMessage saveCarFans(CarFans carFans){
        return carService.saveCarFans(carFans);
    }

    /**
     * 获取关注的车型
     * @param accountid
     * @return
     */
    @RequestMapping("/fans/get")
    public NetMessage getCarFans(String accountid){
        return carService.getCarFans(accountid);
    }

    /******************************************************************/
    /******************************************************************/
    /******************************************************************/
    /******************************************************************/
    /******************************************************************/

    /**
     * 录入汽车品牌
     * @param carBrand 汽车品牌对象
     * @return
     */
    @RequestMapping("/save/brand")
    public NetMessage saveBrand(CarBrand carBrand, MultipartFile file) {
        return carService.saveBrand(carBrand,file);
    }


}
