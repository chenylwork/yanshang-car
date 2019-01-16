package com.yanshang.car.controller;

import com.yanshang.car.bean.*;
import com.yanshang.car.commons.NetMessage;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @RequestMapping("/recommend/{brand}")
    public NetMessage recommend(@PathVariable("brand") String brand) {
        return null;
    }


    /**
     * 根据汽车标识获取汽车详细信息
     * @param carIdentity 汽车标识
     * @return
     */
    @RequestMapping("/details")
    public NetMessage details(String carIdentity) {
        return null;
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
     * 根据标识获取租车信息
     * @param identity
     * @return
     */
    @RequestMapping("/rent")
    public NetMessage rent(String identity) {
        return null;
    }

    /**
     * 添加租车订单
     * @param order 订单信息
     * @return
     */
    @RequestMapping("/order/rent/save")
    public NetMessage saveRentOrder(RentOrder order) {
        return null;
    }

    /**
     * 获取所有的试驾车
     * @param identity
     * @return
     */
    @RequestMapping("/tests")
    public NetMessage tests(String identity) {
        return null;
    }

    /**
     * 根据唯一标识获取试驾车信息
     * @param identity
     * @return
     */
    @RequestMapping("/test")
    public NetMessage test(String identity) {
        return null;
    }

    /**
     * 添加试驾订单
     * @param order
     * @return
     */
    @RequestMapping("/order/test/save")
    public NetMessage saveTestOrder(TestOrder order) {
        return null;
    }


}
