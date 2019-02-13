package com.yanshang.car.repositories;

import com.yanshang.car.bean.AccountActivity;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * @ClassName AccountActivityRepository
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2019/2/13- 9:51
 * @Version 1.0
 **/
@Repository
public interface AccountActivityRepository
        extends JpaRepositoryImplementation<AccountActivity,Integer>{


}
