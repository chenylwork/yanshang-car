package com.yanshang.car.repositories;

import com.yanshang.car.bean.view.FansView;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * @ClassName FansViewRepository
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2019/2/13- 14:08
 * @Version 1.0
 **/
@Repository
public interface FansViewRepository
        extends JpaRepositoryImplementation<FansView,Integer>{
    /**
     * 获取粉丝集合
     * @param accountid
     * @return
     */
    List<FansView> getByFromuser(String accountid);

    /**
     * 获取我的关注集合
     * @param accountid
     * @return
     */
    List<FansView> getByTouser(String accountid);
}
