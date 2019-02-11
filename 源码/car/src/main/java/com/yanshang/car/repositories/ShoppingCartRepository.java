package com.yanshang.car.repositories;

import com.yanshang.car.bean.ShoppingCart;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * @ClassName ShopingCartRepository
 * @Description 购物车数据操作
 * @Author 陈彦磊
 * @Date 2019/2/11- 14:29
 * @Version 1.0
 **/
@Repository
public interface ShoppingCartRepository extends JpaRepositoryImplementation<ShoppingCart,Integer> {
    /**
     * 根据用户编号获取购物车信息
     * @param accountid
     * @return
     */
    List<ShoppingCart> getByAccountid(String accountid);
}
