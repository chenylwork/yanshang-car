package com.yanshang.car.repositories;

import com.yanshang.car.bean.Activity;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

/*
 * @ClassName ActivityRepository
 * @Description 活动操作映射
 * @Author 陈彦磊
 * @Date 2019/2/13- 9:57
 * @Version 1.0
 **/
@Repository
public interface ActivityRepository
        extends JpaRepositoryImplementation<Activity,Integer>{
}
