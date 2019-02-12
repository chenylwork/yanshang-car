package com.yanshang.car.repositories;

import com.yanshang.car.bean.Idea;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/*
 * @ClassName IdeaRepository
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2019/2/12- 17:36
 * @Version 1.0
 **/
@Repository
public interface IdeaRepository extends MongoRepository<Idea,String>{

}
