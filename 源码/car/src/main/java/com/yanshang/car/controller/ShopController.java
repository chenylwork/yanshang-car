package com.yanshang.car.controller;

import com.yanshang.car.bean.Goods;
import com.yanshang.car.bean.ShoppingAddress;
import com.yanshang.car.bean.ShoppingCart;
import com.yanshang.car.commons.NetMessage;
import com.yanshang.car.services.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public NetMessage saveGoods(String goods) {
        return shopService.saveGoods(goods);
    }
    @RequestMapping("/goods/get")
    public NetMessage getGoods(@RequestParam Map<String,String> data) {
        return shopService.getGoods(data);
    }
    @RequestMapping("/keyword")
    public NetMessage keyword() {
        return shopService.keyword();
    }
    @RequestMapping("/cart/save")
    public NetMessage saveShoppingCart(ShoppingCart shoppingCart) {
        return shopService.saveShoppingCart(shoppingCart);
    }
    @RequestMapping("/cart/get")
    public NetMessage getShoppingCart(@RequestParam Map<String,String> data) {
        return shopService.getShoppingCart(data);
    }
    @RequestMapping("/address/save")
    public NetMessage saveShoppingAddress(ShoppingAddress shoppingAddress) {
        return shopService.saveShoppingAddress(shoppingAddress);
    }
    @RequestMapping("/address/get")
    public NetMessage getShoppingAddress(@RequestParam Map<String,String> data) {
        return shopService.getShoppingAddress(data);
    }
    @Autowired
    private ShopService shopService;
}

