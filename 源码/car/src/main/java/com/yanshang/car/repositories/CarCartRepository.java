package com.yanshang.car.repositories;

import com.yanshang.car.bean.CarCart;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * @ClassName CarCartRepository
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2019/1/28- 9:34
 * @Version 1.0
 **/
@Repository
public interface CarCartRepository extends JpaRepositoryImplementation<CarCart,Integer>{

    /**
     * 根据用户编号获取器购物车信息
     * @param userid
     * @return
     */
    List<CarCart> getByUseridOrderByTimeDesc(String userid);
}
