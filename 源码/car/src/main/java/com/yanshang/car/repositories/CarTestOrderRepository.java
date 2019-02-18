package com.yanshang.car.repositories;

import com.yanshang.car.bean.TestOrder;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

/*
 * @ClassName CarTestOrderRepository
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2019/1/28- 14:24
 * @Version 1.0
 **/
@Repository
public interface CarTestOrderRepository extends JpaRepositoryImplementation<TestOrder,String>{

}
