package com.yanshang.car.services;

import com.yanshang.car.bean.ShoppingAddress;
import com.yanshang.car.bean.ShoppingCart;
import com.yanshang.car.bean.ShoppingOrder;
import com.yanshang.car.bean.ShoppingScore;
import com.yanshang.car.commons.NetMessage;
import org.springframework.web.multipart.MultipartFile;

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
     String GOODS_KEYWORD_KEY = "goods_keyword";
    /**
     * 商品库存数
     */
    String GOODS_COUNT_KEY = "size";
    /**
     * 保存商品信息
     * @param goods
     * @return
     */
     NetMessage saveGoods(Map<String,Object> goods, MultipartFile... files);

    /**
     * 获取商品信息
     * goodsid 商品编号
     * name 商品名称
     * @param data
     * @return
     */
     NetMessage getGoods(Map<String,String> data) ;

    /**
     * 获取查询关键字
     * @return
     */
     NetMessage getKeyword(String keyword);

    /**
     * 新加关键字
     * @return
     */
     NetMessage saveKeyword(String keyword);

    /**
     * 保存购物车信息
     * @param shoppingCart
     * @return
     */
     NetMessage saveShoppingCart(ShoppingCart shoppingCart) ;

    /**
     * 删除购物车订单
     * @param cartid
     * @return
     */
    NetMessage delShoppingCart(String... cartid);

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
     NetMessage getShoppingAddress(Map<String,String> data);

    /**
     * 保存订单
     * @param order
     * @return
     */
     NetMessage saveOrder(ShoppingOrder order);

    /**
     * 获取商品订单
     * @param data
     * @return
     */
    NetMessage getOrder(Map<String,String> data);

    /**
     * 删除购物订单
     * @param data
     * @return
     */
    NetMessage delOrder(Map<String,String> data);
    /**
     * 保存商品积分
     * @param shoppingScore
     * @return
     */
    NetMessage saveShoppingScore(ShoppingScore shoppingScore);

    /**
     * 用户今天是否签到
     * @param accountid
     * @return
     */
    NetMessage dayIsSignin(String accountid);

    /**
     *
     * @param accountid 用户编号
     * @return
     */
    NetMessage getShoppingScore(String accountid);

}
