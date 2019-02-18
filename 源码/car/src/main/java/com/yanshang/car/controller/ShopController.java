package com.yanshang.car.controller;

import com.yanshang.car.bean.*;
import com.yanshang.car.commons.NetMessage;
import com.yanshang.car.services.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;


        /*
 * @ClassName ShopController
 * @Description 商城接口
 * @Author 陈彦磊
 * @Date 2019/2/2- 11:55
 * @Version 1.0
 **/

@RestController
@RequestMapping("/shop")
public class ShopController {

    /**
     * 保存商品信息
     * @param goods
     * @return
     */
    @RequestMapping("/goods/save")
    public NetMessage saveGoods(@RequestParam Map<String,Object> goods, MultipartFile... file) {
        return shopService.saveGoods(goods,file);
    }
    @RequestMapping("/goods/del")
    public NetMessage delGoods(String ... goodsid) {
        return shopService.delShoppingCart(goodsid);
    }

    /**
     * 获取商品信息
     * @param data
     * @return
     */
    @RequestMapping("/goods/get")
    public NetMessage getGoods(@RequestParam Map<String,String> data) {
        return shopService.getGoods(data);
    }

    /**
     * 获取搜索关键字
     * @return
     */
    @RequestMapping("/keyword/get")
    public NetMessage keyword(String keyword) {
        return shopService.getKeyword(keyword);
    }

    /**
     * 保存搜索关键字
     * @param keyword
     * @return
     */
    @RequestMapping("/keyword/save")
    public NetMessage saveKeyword(String keyword) {
        return shopService.saveKeyword(keyword);
    }

    /**
     * 保存购物车
     * @param shoppingCart
     * @return
     */
    @RequestMapping("/cart/save")
    public NetMessage saveShoppingCart(ShoppingCart shoppingCart) {
        return shopService.saveShoppingCart(shoppingCart);
    }

    /**
     * 获取购物车
     * @param data
     * @return
     */
    @RequestMapping("/cart/get")
    public NetMessage getShoppingCart(@RequestParam Map<String,String> data) {
        return shopService.getShoppingCart(data);
    }

    /**
     * 删除购物车列表
     * @param cartid
     * @return
     */
    @RequestMapping("/cart/del")
    public NetMessage delShoppingCart(String... cartid) {
        return shopService.delShoppingCart(cartid);
    }
    /**
     * 保存收货地址
     * @param shoppingAddress
     * @return
     */
    @RequestMapping("/address/save")
    public NetMessage saveShoppingAddress(ShoppingAddress shoppingAddress) {
        return shopService.saveShoppingAddress(shoppingAddress);
    }

    /**
     * 获取收货地址
     * @param data
     * @return
     */
    @RequestMapping("/address/get")
    public NetMessage getShoppingAddress(@RequestParam Map<String,String> data) {
        return shopService.getShoppingAddress(data);
    }

    /**
     * 保存订单信息
     * @param order
     * @return
     */
    @RequestMapping("/order/save")
    public NetMessage saveOrder(ShoppingOrder order) {
        return shopService.saveOrder(order);
    }

    /**
     * 获取订单信息
     * @param data
     * @return
     */
    @RequestMapping("/order/get")
    public NetMessage getOrder(@RequestParam Map<String,String> data) {
        return shopService.getOrder(data);
    }

    /**
     * 删除购物订单
     * @param data
     * @return
     */
    @RequestMapping("/order/del")
    public NetMessage delOrder(@RequestParam Map<String,String> data) {
        return shopService.delOrder(data);
    }
    /**
     * 保存积分信息
     * @param shoppingScore
     * @return
     */
    @RequestMapping("/scope/save")
    public NetMessage saveScope(ShoppingScore shoppingScore) {
        return shopService.saveShoppingScore(shoppingScore);
    }

    @RequestMapping("/signin")
    public NetMessage signin(ShoppingScore shoppingScore) {
        shoppingScore.setName("签到");
        shoppingScore.setScore(5);
        NetMessage netMessage = shopService.saveShoppingScore(shoppingScore);
        return (netMessage.getStatus() == NetMessage.FAIl)?
                netMessage.setContent("签到失败！！"):
                netMessage.setContent("签到成功！！");
    }

    /**
     * 判断今日是否签到
     * @param accountid
     * @return
     */
    @RequestMapping("/signin/is")
    public NetMessage signinIs(String accountid) {
        return shopService.dayIsSignin(accountid);
    }

    /**
     * 获取积分详情
     * @param accountid
     * @return
     */
    @RequestMapping("/scope/get")
    public NetMessage getScope(String accountid) {
        return shopService.getShoppingScore(accountid);
    }
    @Autowired
    private ShopService shopService;
}

