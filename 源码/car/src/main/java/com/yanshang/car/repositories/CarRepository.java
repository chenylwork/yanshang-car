package com.yanshang.car.repositories;

import com.yanshang.car.bean.Car;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

/*
 * @ClassName CarRepository
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2019/1/16- 14:26
 * @Version 1.0
 **/
@Repository
public interface CarRepository extends JpaRepositoryImplementation<Car,Integer>{
}
