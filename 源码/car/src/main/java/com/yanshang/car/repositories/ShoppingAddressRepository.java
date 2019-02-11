package com.yanshang.car.repositories;

import com.yanshang.car.bean.ShoppingAddress;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * @ClassName ShoppingAddressRepository
 * @Description 购物地址
 * @Author 陈彦磊
 * @Date 2019/2/11- 14:44
 * @Version 1.0
 **/
@Repository
public interface ShoppingAddressRepository extends JpaRepositoryImplementation<ShoppingAddress,Integer>{
    /**
     * 根据用户编号获取
     * @param accountid
     * @return
     */
    List<ShoppingAddress> getByAccountid(String accountid);
}
