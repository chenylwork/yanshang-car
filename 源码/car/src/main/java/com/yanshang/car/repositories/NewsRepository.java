package com.yanshang.car.repositories;

import com.yanshang.car.bean.News;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * @ClassName NewsRepository
 * @Description 发现品牌数据操作
 * @Author 陈彦磊
 * @Date 2019/1/29- 10:41
 * @Version 1.0
 **/
@Repository
public interface NewsRepository  extends JpaRepositoryImplementation<News,Integer>{

    /**
     * 根据用户编号获取新闻
     * @param userid
     * @return
     */
    List<News> getByUserid(String userid);
}
