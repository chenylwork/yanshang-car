package com.yanshang.car.repositories;

import com.yanshang.car.bean.NewsComment;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

/*
 * @ClassName NewsCommentRepository
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2019/1/29- 11:07
 * @Version 1.0
 **/
@Repository
public interface NewsCommentRepository extends JpaRepositoryImplementation<NewsComment,Integer>{
}
