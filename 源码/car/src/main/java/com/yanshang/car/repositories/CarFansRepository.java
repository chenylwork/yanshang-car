package com.yanshang.car.repositories;

import com.yanshang.car.bean.CarFans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * @ClassName CarFansRepository
 * @Description 关注的汽车数据操作
 * @Author 陈彦磊
 * @Date 2019/2/12- 17:05
 * @Version 1.0
 **/
@Repository
public interface CarFansRepository extends JpaRepositoryImplementation<CarFans,Integer>{
    /**
     * 根据用户编号获取关注车型
     * @param accountid
     * @return
     */
    List<CarFans> getByAccountid(String accountid);
}
