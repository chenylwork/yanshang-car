package com.yanshang.car.repositories;

import com.yanshang.car.bean.CarBrand;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

/*
 * @ClassName CarBrandRepository
 * @Description 汽车品牌操作
 * @Author 陈彦磊
 * @Date 2019/1/17- 9:53
 * @Version 1.0
 **/
@Repository
public interface CarBrandRepository extends JpaRepositoryImplementation<CarBrand,Integer> {

    /**
     * 根据品牌名称获取信息
     * @param name
     * @return
     */
    CarBrand getByName(String name);

}
