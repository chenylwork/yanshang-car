package com.yanshang.car.repositories;

import com.yanshang.car.bean.Recommend;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * @ClassName RecommendRepository
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2019/2/13- 14:58
 * @Version 1.0
 **/
@Repository
public interface RecommendRepository
        extends JpaRepositoryImplementation<Recommend,Integer>{
    /**
     * 获取推荐人
     * @param accountid
     * @return
     */
    List<Recommend> getByFromuser(String accountid);

    /**
     * 获取我推荐的人
     * @param accountid
     * @return
     */
    List<Recommend> getByTouser(String accountid);
}
