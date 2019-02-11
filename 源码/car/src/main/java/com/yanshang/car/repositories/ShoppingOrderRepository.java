package com.yanshang.car.repositories;

import com.yanshang.car.bean.ShoppingOrder;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * @ClassName ShoppingOrderRepository
 * @Description 购物订单操作
 * @Author 陈彦磊
 * @Date 2019/2/11- 14:51
 * @Version 1.0
 **/
@Repository
public interface ShoppingOrderRepository extends JpaRepositoryImplementation<ShoppingOrder,Integer>{
    /**
     * 根据用户编号获取
     * @param accountid
     * @return
     */
    List<ShoppingOrder> getByAccountid(String accountid);
}
