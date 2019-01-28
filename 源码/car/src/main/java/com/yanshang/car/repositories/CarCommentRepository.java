package com.yanshang.car.repositories;

import com.yanshang.car.bean.CarComment;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * @ClassName CarCommentRepository
 * @Description 汽车评论操作
 * @Author 陈彦磊
 * @Date 2019/1/17- 13:58
 * @Version 1.0
 **/
@Repository
public interface CarCommentRepository extends JpaRepositoryImplementation<CarComment,Integer> {
}
