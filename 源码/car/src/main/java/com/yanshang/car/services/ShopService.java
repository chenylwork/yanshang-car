package com.yanshang.car.services;

import com.yanshang.car.bean.Goods;
import com.yanshang.car.bean.ShoppingAddress;
import com.yanshang.car.bean.ShoppingCart;
import com.yanshang.car.commons.NetMessage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

/*
 * @ClassName ShopService
 * @Description 商城操作
 * @Author 陈彦磊
 * @Date 2019/2/2- 16:57
 * @Version 1.0
 **/
 public interface ShopService {
     String GOODS_COLLECTIONS_NAME = "goods";
    /**
     * 保存商品信息
     * @param goods
     * @return
     */
     NetMessage saveGoods(String goods);

    /**
     * 获取商品信息
     * @param data
     * @return
     */
     NetMessage getGoods(Map<String,String> data) ;

    /**
     * 获取查询关键字
     * @return
     */
     NetMessage keyword() ;

    /**
     * 保存购物车信息
     * @param shoppingCart
     * @return
     */
     NetMessage saveShoppingCart(ShoppingCart shoppingCart) ;

    /**
     * 查询获取购物车信息
     * @param data
     * @return
     */
     NetMessage getShoppingCart(Map<String,String> data) ;

    /**
     * 保存购物地址
     * @param shoppingAddress
     * @return
     */
     NetMessage saveShoppingAddress(ShoppingAddress shoppingAddress) ;

    /**
     * 查询获取购物地址
     * @param data
     * @return
     */
     NetMessage getShoppingAddress(Map<String,String> data) ;
}
