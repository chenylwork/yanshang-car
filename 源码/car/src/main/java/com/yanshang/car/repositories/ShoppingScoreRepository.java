package com.yanshang.car.repositories;

import com.yanshang.car.bean.ShoppingScore;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * @ClassName ShoppingScoreRepository
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2019/2/12- 9:05
 * @Version 1.0
 **/
@Repository
public interface ShoppingScoreRepository extends JpaRepositoryImplementation<ShoppingScore,Integer>{

    /**
     * 根据用户编号获取积分信息
     * @param accountid
     * @return
     */
    List<ShoppingScore> getByAccountid(String accountid);

}
