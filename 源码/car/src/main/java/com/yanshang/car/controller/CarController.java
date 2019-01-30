package com.yanshang.car.controller;

import com.yanshang.car.bean.*;
import com.yanshang.car.commons.NetMessage;
import com.yanshang.car.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
     * 保存汽车信息
     * @param data
     * @return
     */
    @RequestMapping("/save/info")
    public NetMessage saveInfo(String data) {
        return carService.saveInfo(data);
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

    /**
     * 获取汽车详细配置信息
     * @param identity 汽车标识
     * @return
     */
    @RequestMapping("/details")
    public NetMessage details(String identity) {
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
     * 根据汽车唯一标识获取汽车的所有颜色
     * @param identity
     * @return
     */
    @RequestMapping("/colors")
    public NetMessage colors(String identity) {
        return null;
    }

    @RequestMapping("/colors/save")
    public NetMessage saveColors(String color,MultipartFile file) {
        return null;
    }

    /**
     * 根据汽车标识获取汽车的所有类型
     * @param identity
     * @return
     */
    @RequestMapping("/types")
    public NetMessage types(String identity) {
        return null;
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
    @RequestMapping("/price/save")
    public NetMessage savePrice(String data) {
        return carService.saveCarPrice(data);
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
     * 根据汽车唯一标识获取该汽车的贷款信息
     * @param identity 汽车唯一标识
     * @return
     */
    @RequestMapping("/loan")
    public NetMessage loan(String identity) {
        return null;
    }

    /**
     * 保存贷款订单
     * @param order
     * @return
     */
    @RequestMapping("/order/loan/save")
    public NetMessage saveLoanOrder(LoanOrder order) {
        return null;
    }

    /**
     * 保存全款订单
     * @param order
     * @return
     */
    @RequestMapping("/order/once/save")
    public NetMessage saveLoanOrder(OnceOrder order) {
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
     * 获取时间信息
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
