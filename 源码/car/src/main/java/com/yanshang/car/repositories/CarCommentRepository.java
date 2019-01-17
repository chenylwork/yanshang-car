package com.yanshang.car.repositories;

import com.yanshang.car.bean.CarComment;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.List;

/*
 * @ClassName CarCommentRepository
 * @Description 汽车评论操作
 * @Author 陈彦磊
 * @Date 2019/1/17- 13:58
 * @Version 1.0
 **/
public interface CarCommentRepository extends JpaRepositoryImplementation<CarComment,Integer> {

    /**
     * 根据汽车标识获取，对应的评论
     * @param object 汽车标识
     * @return
     */
    List<CarComment> getByObject(String object);
}
