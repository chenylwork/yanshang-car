package com.yanshang.car.repositories;

import com.yanshang.car.bean.Fans;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * @ClassName FansRepository
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2019/2/12- 14:37
 * @Version 1.0
 **/
@Repository
public interface FansRepository extends JpaRepositoryImplementation<Fans,Integer>{
    /**
     * 获取我的粉丝列表
     * @param fromuser
     * @return
     */
    List<Fans> getByFromuser(String fromuser);

    /**
     * 根据关注者和被关注着确定一个关系
     * @param fromuser
     * @param touser
     * @return
     */
    Fans getByFromuserAndTouser(String fromuser,String touser);
    /**
     * 获取我的关注的列表
     * @param touser
     * @return
     */
    List<Fans> getByTouser(String touser);

    /**
     * 获取我的粉丝个数
     * @param fromuser
     * @return
     */
    long countByFromuser(String fromuser);

    /**
     * 获取我关注的个数
     * @param touser
     * @return
     */
    long countByTouser(String touser);

}
