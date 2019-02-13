package com.yanshang.car.repositories;

import com.yanshang.car.bean.view.RecommendView;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * @ClassName RecommendViewRepository
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2019/2/13- 15:06
 * @Version 1.0
 **/
@Repository
public interface RecommendViewRepository
        extends JpaRepositoryImplementation<RecommendView,Integer>{
    /**
     * 获取推荐人
     * @param accountid
     * @return
     */
    List<RecommendView> getByFromuser(String accountid);

    /**
     * 获取我推荐的人
     * @param accountid
     * @return
     */
    List<RecommendView> getByTouser(String accountid);
}
