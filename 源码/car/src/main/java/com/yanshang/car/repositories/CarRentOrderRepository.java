package com.yanshang.car.repositories;

import com.yanshang.car.bean.RentOrder;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

/*
 * @ClassName CarRentOrderRepository
 * @Description 汽车出租订单管理
 * @Author 陈彦磊
 * @Date 2019/1/28- 11:29
 * @Version 1.0
 **/
@Repository
public interface CarRentOrderRepository extends JpaRepositoryImplementation<RentOrder,String>{

}
